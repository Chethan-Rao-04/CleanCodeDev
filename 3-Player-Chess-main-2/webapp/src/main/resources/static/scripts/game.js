/* Updated game.js */

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
            square.addEventListener('click', () => sendPolygonClicked(square.id));
            boardGroup.appendChild(square);
        }
    }
}

// Update the current player based on the server response
function updateCurrentPlayer(color) {
    const colourName = colorMap[color];
    const playerName = localStorage.getItem(colourName);

    const p_name = document.getElementById('pl-name');
    p_name.textContent = playerName || 'Unknown';

    const p_colour = document.getElementById('pl-colour');
    p_colour.style.color = colourName === 'White' ? '#FFFFFF' : '#000000'; // White or Black
}

// Update the board after a response from the server
function updateBoard(response) {
    clearBoard();  // Clear previous board state
    console.log('New Board Configuration:', response);

    const board = response['board'];  // Board positions and pieces
    const highlightedPolygons = response['highlightedPolygons'];  // Highlighted squares for valid moves
    const winner = response['winner'];

    if (response['gameOver']) {
        showGameOverPopup(winner);  // Show Game Over popup if the game is over
    }

    updatePieces(board);  // Render the pieces
    displayPossibleMoves(highlightedPolygons);  // Highlight possible moves
}

// Render the pieces on the board
function updatePieces(board) {
    const piecesGroup = document.getElementById('pieces');
    piecesGroup.innerHTML = '';  // Clear existing pieces

    for (const pos in board) {
        const value = board[pos];  // Get piece details for this position
        const pieceColor = value[0];  // White or Black
        const pieceToken = value[1];  // Type of piece (e.g., 'R', 'P')

        // Convert position (e.g., 'a1', 'd4') to coordinates
        const col = pos.charCodeAt(0) - 97;  // Convert 'a' -> 0, 'b' -> 1, ...
        const row = 8 - parseInt(pos[1]);  // Convert '1' -> 7, '2' -> 6, ...

        const x = col * 120 + 60;  // Calculate X position for piece
        const y = row * 120 + 60;  // Calculate Y position for piece

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
    textElement.setAttribute('fill', colorMap[color] === 'White' ? 'white' : 'black');  // Set piece color
    textElement.setAttribute('font-size', '50');
    textElement.setAttribute('font-weight', 'bold');
    textElement.textContent = pieceMap[pieceToken];  // Set piece symbol
    textElement.setAttribute('class', theme);  // Apply the selected font theme
    console.log(`Rendering piece: ${pieceToken} at (${x}, ${y}) with color ${colorMap[color]}`);
    return textElement;
}

// Clear all pieces from the board
function clearBoard() {
    const piecesGroup = document.getElementById('pieces');
    piecesGroup.innerHTML = '';
}

// Send the clicked polygon (square) to the server for processing
function sendPolygonClicked(polygonId) {
    const request = new XMLHttpRequest();
    request.open("POST", "/onClick", false);
    request.send(polygonId);

    if (request.status === 200) {
        const data = JSON.parse(request.response);
        updateBoard(data);
    }
}

// Request the updated board from the server
function requestUpdatedBoard() {
    console.log("Request Current Board");
    const request = new XMLHttpRequest();
    request.open("GET", "/board", false);
    request.send(null);

    if (request.status === 200) {
        const data = JSON.parse(request.response);
        updateBoard(data);
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
function displayPossibleMoves(highlightedPolygons) {
    const board = document.getElementById('board');

    highlightedPolygons.forEach(polygonId => {
        const square = document.getElementById(polygonId);
        if (square) {
            square.setAttribute('fill', 'rgba(255, 255, 0, 0.5)');  // Highlight the square with yellow
        }
    });
}
