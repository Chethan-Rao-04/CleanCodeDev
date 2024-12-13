const pieceMap = {
    'R': '\u2656', 'N': '\u2658', 'B': '\u2657', 'Q': '\u2655', 'K': '\u2654', 'P': '\u2659',
    'r': '\u265C', 'n': '\u265E', 'b': '\u265D', 'q': '\u265B', 'k': '\u265A', 'p': '\u265F'
};

const colorMap = {'W': 'White', 'B': 'Black'};

let theme = 'arialTheme';  // Default theme

// Initialize the game when the page loads
function bodyLoaded() {
    console.log("Body loaded");
    renderBoard(); // Ensure board is rendered
    requestUpdatedBoard();
    requestCurrentPlayer();
}

// Render the chessboard squares dynamically
function renderBoard() {
    const boardGroup = document.getElementById('board');
    boardGroup.innerHTML = ''; // Clear existing squares

    for (let row = 0; row < 8; row++) {
        for (let col = 0; col < 8; col++) {
            const square = document.createElementNS('http://www.w3.org/2000/svg', 'rect');
            square.setAttribute('x', col * 120);
            square.setAttribute('y', row * 120);
            square.setAttribute('width', 120);
            square.setAttribute('height', 120);
            square.setAttribute('fill', (row + col) % 2 === 0 ? '#f0d9b5' : '#b58863'); // Checkerboard pattern
            square.setAttribute('id', `${String.fromCharCode(97 + col)}${8 - row}`);
            square.addEventListener('click', () => sendSquareClicked(square.id));
            boardGroup.appendChild(square);
        }
    }
}

// Update the current player based on the server response
function updateCurrentPlayer(color) {
    const colourName = colorMap[color];
    const playerName = localStorage.getItem(colourName);

    const p_name = document.getElementById('pl-name');
    p_name.textContent = playerName || 'Current Player';

    const p_colour = document.getElementById('pl-colour');
    p_colour.style.color = colourName === 'White' ? '#FFFFFF' : '#000000'; // White or Black
}

// Update the board after a response from the server
function updateBoard(response) {
    clearBoard();  // Clear previous board state
    console.log('New Board Configuration:', response);

    const board = response['board'];
    updatePieces(board);// Board positions and pieces
    const highlightedSquares = response['highlightedSquares'];  // Highlighted squares for valid moves
    const winner = response['winner'];

    if (response['gameOver']) {
        showGameOverPopup(winner);  // Show Game Over popup if the game is over
    }

      // Render the pieces
    displayPossibleMoves(highlightedSquares);  // Highlight possible moves
}

// Render the pieces on the board
function updatePieces(board) {
    const piecesGroup = document.getElementById('pieces');
    piecesGroup.innerHTML = '';  // Clear existing pieces

    for (const pos in board) {
        const value = board[pos];  // Get piece details for this position
        const pieceColor = value[0];  // White or Black
        const pieceToken = value[1];  // Type of piece (e.g., 'R', 'P')

        // Strip the color prefix from the position
        const actualPos = pos.substring(1);

        // Convert position (e.g., 'a1', 'd4') to coordinates
        const col = actualPos.charCodeAt(0) - 97;  // Convert 'a' -> 0, 'b' -> 1, ...
        const row = 8 - parseInt(actualPos[1], 10);  // Convert '1' -> 7, '2' -> 6, ...

        if (isNaN(col) || isNaN(row)) {
            console.error(`Invalid position: ${pos}`);
            continue;
        }

        const x = col * 120 + 60;  // Calculate X position for piece
        const y = row * 120 + 60;  // Calculate Y position for piece

        console.log(`Rendering piece: ${pieceToken} at (${x}, ${y}) with color ${colorMap[pieceColor]}`);
        const textElement = getPieceText(x, y, pieceColor, pieceToken);
        piecesGroup.appendChild(textElement);  // Add piece to the SVG group
    }
}


// Helper function to create a text element for a piece
function getPieceText(x, y, color, pieceToken) {
    const textElement = document.createElementNS('http://www.w3.org/2000/svg', 'text');
    textElement.setAttribute('x', x);
    textElement.setAttribute('y', y);
    textElement.setAttribute("text-anchor", "middle");
    textElement.setAttribute("dominant-baseline", "middle");
    textElement.setAttribute('fill', color === 'W' ? 'white' : 'black');  // Set piece color
    textElement.setAttribute('font-size', '40');  // Adjusted font size for visibility
    textElement.setAttribute('font-weight', 'bold');
    textElement.textContent = pieceMap[pieceToken];  // Set piece symbol
    textElement.setAttribute('style', `font-family: ${theme};`);  // Apply the selected font theme
    console.log(`Rendering piece: ${pieceToken} at (${x}, ${y}) with color ${color === 'W' ? 'White' : 'Black'}`);
    return textElement;
}

// Clear all pieces from the board
function clearBoard() {
    const piecesGroup = document.getElementById('pieces');
    piecesGroup.innerHTML = '';
}

// Send the clicked square to the server for processing
function sendSquareClicked(squareId) {
    const request = new XMLHttpRequest();
    request.open("POST", "/onClick", true);
    request.setRequestHeader("Content-Type", "application/json");  // Ensure it's JSON
    request.send(JSON.stringify({ squareId: squareId }));
    request.onload = function() {
    if (request.status === 200) {
        const data = JSON.parse(request.responseText);
        updateBoard(data);
    } else {
        console.error("Failed to send square click:", request.status, request.statusText);
    }
    };
}

// Request the updated board from the server
function requestUpdatedBoard() {
    console.log("Requesting Current Board");
    const request = new XMLHttpRequest();
    request.open("GET", "/board", false);
    request.send(null);

    if (request.status === 200) {
        const data = JSON.parse(request.response);
        console.log("Board Data:", data);
        updateBoard(data);
    } else {
        console.error("Failed to fetch board data:", request.status, request.statusText);
    }
}

// Request the current player from the server
function requestCurrentPlayer() {
    const request = new XMLHttpRequest();
    request.open("GET", "/currentPlayer", false);
    request.send(null);

    if (request.status === 200) {
        const player = request.response;
        updateCurrentPlayer(player);  // Update the player on the board
    } else {
        console.error("Failed to fetch current player:", request.status, request.statusText);
    }
}

// Show a game over popup when the game ends
function showGameOverPopup(winner) {
    const colourName = colorMap[winner];
    const playerName = localStorage.getItem(colourName);
    document.getElementById('popup').style.display = 'block';
    const winnerText = `${playerName} (${colourName}) has won the Game!`;
    document.getElementById('winner').innerText = winnerText;
}

// Close the game over popup
function closePopup() {
    document.getElementById('popup').style.display = 'none';
}

// Display possible moves for a piece (highlighted squares)
function displayPossibleMoves(highlightedSquares) {
    const board = document.getElementById('board');

    if (highlightedSquares) {
        highlightedSquares.forEach(squareId => {
            const square = document.getElementById(squareId);
            if (square) {
                square.setAttribute('fill', 'rgba(255, 255, 0, 0.5)');  // Highlight the square with yellow
            }
        });
    }
}
