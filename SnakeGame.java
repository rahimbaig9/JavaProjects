import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class SnakeGame extends JFrame implements KeyListener, ActionListener {
    private static final int GRID_SIZE = 20;
    private static final int CELL_SIZE = 30;
    private static final int GAME_WIDTH = GRID_SIZE * CELL_SIZE;
    private static final int GAME_HEIGHT = GRID_SIZE * CELL_SIZE;

    private int[] snakeX, snakeY;
    private int foodX, foodY;
    private int snakeLength;
    private int score, highScore;
    private boolean gameOver;
    private int dx, dy;

    public SnakeGame() {
        setTitle("Snake Game");
        setSize(GAME_WIDTH, GAME_HEIGHT);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        snakeX = new int[GRID_SIZE * GRID_SIZE];
        snakeY = new int[GRID_SIZE * GRID_SIZE];

        initGame();
        addKeyListener(this);

        Timer timer = new Timer(200, this);
        timer.start();
    }

    private void initGame() {
        snakeLength = 1;
        snakeX[0] = GRID_SIZE / 2;
        snakeY[0] = GRID_SIZE / 2;
        spawnFood();
        score = 0;
        dx = 0;
        dy = 0;
        gameOver = false;
    }

   private void spawnFood() {
    Random random = new Random();
    boolean validPosition = false;

    while (!validPosition) {
        foodX = random.nextInt(GRID_SIZE);
        foodY = random.nextInt(GRID_SIZE);
        validPosition = true;

        // Check if the food is spawning on the snake's body
        for (int i = 0; i < snakeLength; i++) {
            if (foodX == snakeX[i] && foodY == snakeY[i]) {
                validPosition = false;
                break;
            }
        }
    }
}


    public void paint(Graphics g) {
        super.paint(g);
        if (gameOver) {
            displayGameOver(g);
        } else {
            drawSnake(g);
            drawFood(g);
        }
    }

    private void drawSnake(Graphics g) {
        g.setColor(Color.RED);
        for (int i = 0; i < snakeLength; i++) {
            g.fillRect(snakeX[i] * CELL_SIZE, snakeY[i] * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }
    }

    private void drawFood(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect(foodX * CELL_SIZE, foodY * CELL_SIZE, CELL_SIZE, CELL_SIZE);
    }

   
    private void displayGameOver(Graphics g) {
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 24)); // Smaller font size for "Game Over!" message
        String message = "Game Over! Score: " + score + " High Score: " + highScore;
        g.drawString(message, GAME_WIDTH / 2 - g.getFontMetrics().stringWidth(message) / 2, GAME_HEIGHT / 2 - 30);
        
        g.setFont(new Font("Arial", Font.PLAIN, 18)); // Smaller font size for "Try Again" message
        String tryAgainMessage = "Press 'Enter' to Try Again";
        g.drawString(tryAgainMessage, GAME_WIDTH / 2 - g.getFontMetrics().stringWidth(tryAgainMessage) / 2, GAME_HEIGHT / 2 + 20);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_UP && dy == 0) {
            dx = 0;
            dy = -1;
        } else if (key == KeyEvent.VK_DOWN && dy == 0) {
            dx = 0;
            dy = 1;
        } else if (key == KeyEvent.VK_LEFT && dx == 0) {
            dx = -1;
            dy = 0;
        } else if (key == KeyEvent.VK_RIGHT && dx == 0) {
            dx = 1;
            dy = 0;
        } else if (gameOver && key == KeyEvent.VK_ENTER) {
            initGame();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            checkFoodCollision();
            checkWallCollision();
            checkSelfCollision();
            moveSnake();
        }
        repaint();
    }

    private void moveSnake() {
        for (int i = snakeLength - 1; i > 0; i--) {
            snakeX[i] = snakeX[i - 1];
            snakeY[i] = snakeY[i - 1];
        }
        snakeX[0] += dx;
        snakeY[0] += dy;
    }

    private void checkFoodCollision() {
        if (snakeX[0] == foodX && snakeY[0] == foodY) {
            snakeLength++;
            score++;
            if (score > highScore) {
                highScore = score;
            }
            spawnFood();
        }
    }

    private void checkWallCollision() {
        if (snakeX[0] < 0 || snakeX[0] >= GRID_SIZE || snakeY[0] < 0 || snakeY[0] >= GRID_SIZE) {
            gameOver = true;
        }
    }

    private void checkSelfCollision() {
        for (int i = 1; i < snakeLength; i++) {
            if (snakeX[0] == snakeX[i] && snakeY[0] == snakeY[i]) {
                gameOver = true;
                break;
            }
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            SnakeGame snakeGame = new SnakeGame();
            snakeGame.setVisible(true);
        });
    }
}
