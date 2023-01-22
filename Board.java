import java.util.Random;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;


public class Board extends JFrame{
    private final int height;
    private final int length;
    private final String difficulty;
    private int[][] game;
    private boolean[][] moves;
    private int bombs;
    private int empty;
    private int lifes;
    private int hints;
    private static JFrame jFrame = new JFrame();
    private static JPanel stats;
    private static Label lifesLabel;
    private static JButton hintsButton;
    private static JPanel gamePanel;
    private static JButton[][] gameButtons;
    
    
    public Board(){
        height = 4;
        length = 4;
        difficulty = "easy";
        game = new int[4][4];
        moves = new boolean[4][4];
        bombs = 0;
        empty = 0;
        lifes = 6;
        hints = 6;
    }

    public Board(int height, int length, String difficulty, int lifes, int hints){
        this.height = height;
        this.length = length;
        this.difficulty = difficulty;
        game = new int[height][length];
        moves = new boolean[height][length];
        bombs = 0;
        empty = 0;
        this.lifes = lifes;
        this.hints = hints;
    }

    public void init(){
        // Creation of game
        for(int i = 0; i < height; i++){
            for(int j = 0; j < length; j++){
                Random rd = new Random();
                boolean condition;
                if(difficulty == "easy"){
                    condition = rd.nextBoolean() && rd.nextBoolean() && rd.nextBoolean() && rd.nextBoolean();
                }
                else if(difficulty == "medium"){
                    condition = rd.nextBoolean() && rd.nextBoolean();
                }
                else{
                    condition = rd.nextBoolean();
                }

                if(condition){
                    game[i][j] = -1;
                    bombs++;
                }
                else{
                    game[i][j] = 0;
                    empty++;
                }

                moves[i][j] = false;
            }
        }
        // Check intern cells
        for(int i = 0; i < height; i++){
            for(int j = 0; j < length; j++){
                int bomb = 0;
                if(game[i][j] != -1){
                    // Corner
                    if(i == 0 && j == 0){
                        bomb = bomb + ((game[i][j + 1] == -1) ? 1 : 0);
                        bomb = bomb + ((game[i + 1][j + 1] == -1) ? 1 : 0);
                        bomb = bomb + ((game[i + 1][j] == -1) ? 1 : 0);
                    }
                    else if(i == height - 1 && j == 0){
                        bomb = bomb + ((game[i - 1][j] == -1) ? 1 : 0);
                        bomb = bomb + ((game[i - 1][j + 1] == -1) ? 1 : 0);
                        bomb = bomb + ((game[i][j + 1] == -1) ? 1 : 0);
                    }
                    else if(i == 0 && j == length - 1){
                        bomb = bomb + ((game[i][j - 1] == -1) ? 1 : 0);
                        bomb = bomb + ((game[i + 1][j - 1] == -1) ? 1 : 0);
                        bomb = bomb + ((game[i + 1][j] == -1) ? 1 : 0);     
                    }
                    else if(i == height - 1 && j == length - 1){
                        bomb = bomb + ((game[i][j - 1] == -1) ? 1 : 0);
                        bomb = bomb + ((game[i - 1][j - 1] == -1) ? 1 : 0);
                        bomb = bomb + ((game[i - 1][j] == -1) ? 1 : 0);
                    }
                    // Sides
                    else if(i == 0){
                        bomb = bomb + ((game[i][j - 1] == -1) ? 1 : 0);
                        bomb = bomb + ((game[i][j + 1] == -1) ? 1 : 0);
                        bomb = bomb + ((game[i + 1][j - 1] == -1) ? 1 : 0);
                        bomb = bomb + ((game[i + 1][j] == -1) ? 1 : 0);
                        bomb = bomb + ((game[i + 1][j + 1] == -1) ? 1 : 0);
                    }
                    else if(j == 0){
                        bomb = bomb + ((game[i - 1][j] == -1) ? 1 : 0);
                        bomb = bomb + ((game[i + 1][j] == -1) ? 1 : 0);
                        bomb = bomb + ((game[i - 1][j + 1] == -1) ? 1 : 0);
                        bomb = bomb + ((game[i][j + 1] == -1) ? 1 : 0);
                        bomb = bomb + ((game[i + 1][j + 1] == -1) ? 1 : 0);
                    }
                    else if(i == height - 1){
                        bomb = bomb + ((game[i][j - 1] == -1) ? 1 : 0);
                        bomb = bomb + ((game[i][j + 1] == -1) ? 1 : 0);
                        bomb = bomb + ((game[i - 1][j - 1] == -1) ? 1 : 0);
                        bomb = bomb + ((game[i - 1][j] == -1) ? 1 : 0);
                        bomb = bomb + ((game[i - 1][j + 1] == -1) ? 1 : 0);
                    }
                    else if(j == length - 1){
                        bomb = bomb + ((game[i - 1][j] == -1) ? 1 : 0);
                        bomb = bomb + ((game[i + 1][j] == -1) ? 1 : 0);
                        bomb = bomb + ((game[i - 1][j - 1] == -1) ? 1 : 0);
                        bomb = bomb + ((game[i][j - 1] == -1) ? 1 : 0);
                        bomb = bomb + ((game[i + 1][j - 1] == -1) ? 1 : 0);
                    }
                    // Middle
                    else{
                        bomb = bomb + ((game[i - 1][j + 1] == -1) ? 1 : 0);
                        bomb = bomb + ((game[i - 1][j] == -1) ? 1 : 0);
                        bomb = bomb + ((game[i - 1][j - 1] == -1) ? 1 : 0);
                        bomb = bomb + ((game[i][j + 1] == -1) ? 1 : 0);
                        bomb = bomb + ((game[i][j - 1] == -1) ? 1 : 0);
                        bomb = bomb + ((game[i + 1][j + 1] == -1) ? 1 : 0);
                        bomb = bomb + ((game[i + 1][j] == -1) ? 1 : 0);
                        bomb = bomb + ((game[i + 1][j - 1] == -1) ? 1 : 0);
                    }
                    game[i][j] = bomb;
                }
            }
            
        }
    }

    public void print(){
        System.out.println("Game Board");
        for(int i = 0; i < height; i++){
            for(int j = 0; j < length; j++){
                System.out.print("|");
                System.out.print(game[i][j]);
                System.out.print("|");
            }
            System.out.println();
        }
        System.out.println("Moves Board");
        for(int i = 0; i < height; i++){
            for(int j = 0; j < length; j++){
                System.out.print("|");
                System.out.print(moves[i][j]);
                System.out.print("|");
            }
            System.out.println();
        }

        System.out.println("Bombs: " + bombs + ", empty: " + empty);
    }

    public int getStatus(){
        boolean win = true;
        for(int i = 0; i < height; i++){
            for(int j = 0; j < length; j++){
                if(moves[i][j] == false && game[i][j] != -1){
                    win = false;
                }
            }
        }
        if(lifes == 0){
            return 2;
        }
        else if(win == true){
            return 1;
        }
        else{
            return 0;
        }
    }

    private void showAllTiles(){
        for(int i = 0; i < height; i++){
            for(int j = 0; j < length; j++){
                gameButtons[i][j].setEnabled(false);
                moves[i][j] = true;
                if(game[i][j] == -1){
                    ImageIcon bomb = new ImageIcon("./data/bomb.png");
                    gameButtons[i][j].setIcon(bomb);
                }
                else{
                    gameButtons[i][j].setText(Integer.toString(game[i][j]));
                }
            }
        }
    }

    private void showRandomTile(){
        int i, j;
        do{
            Random rand = new Random(); 
            i = rand.nextInt(height);
            j = rand.nextInt(length);
        }while((moves[i][j] != false) && (game[i][j] != -1));
        gameButtons[i][j].setEnabled(false);
        moves[i][j] = true;
        if(game[i][j] == -1){
            ImageIcon bomb = new ImageIcon("./data/bomb.png");
            gameButtons[i][j].setIcon(bomb);
        }
        else{
            gameButtons[i][j].setText(Integer.toString(game[i][j]));
        }
    }

    public void buildGui(){
        stats = new JPanel(new GridLayout(1, 2));
        stats.setBorder(new TitledBorder("Stats"));

        lifesLabel = new Label();
        lifesLabel.setText("Lifes: " + lifes);
        hintsButton = new JButton("Hints: " + hints);
        hintsButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                JButton b = (JButton) e.getSource();
                hints--;
                if(hints == 0){
                    b.setEnabled(false);
                }
                b.setText("Hints: " + hints);
                showRandomTile();
            }
        });

        stats.add(lifesLabel);
        stats.add(hintsButton);

        gamePanel = new JPanel(new GridLayout(height, length));
        gamePanel.setBorder(new TitledBorder("Game"));

        gameButtons = new JButton[height][length];
        for(int i = 0; i < height; i++){
            for(int j = 0; j < length; j++){
                gameButtons[i][j] = new JButton(i + "-" + j);
                gameButtons[i][j].addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        JButton b = (JButton) e.getSource();
                        String s = b.getText();
                        String[] parts = s.split("-");
                        int i = Integer.valueOf(parts[0]);
                        int j = Integer.valueOf(parts[1]);
                        
                        b.setEnabled(false);
                        moves[i][j] = true;
                        if(game[i][j] == -1){
                            ImageIcon bomb = new ImageIcon("./data/bomb.png");
                            b.setIcon(bomb);
                            lifes--;
                            lifesLabel.setText("Lifes: " + lifes);
                        }
                        else{
                            b.setText(Integer.toString(game[i][j]));
                        }
                        // For now
                        int n = getStatus();
                        if(n == 1){
                            // Win
                            JOptionPane.showMessageDialog(null, "You have won");
                            showAllTiles();
                        }
                        if(n == 2){
                            // Lose
                            JOptionPane.showMessageDialog(null, "You have loose");
                            showAllTiles();
                        }
                    }
                });
                gamePanel.add(gameButtons[i][j]);
            }
        }

        jFrame.getContentPane().add(stats, BorderLayout.NORTH);
        jFrame.getContentPane().add(gamePanel, BorderLayout.CENTER);
        
    }

    public void play(){
        jFrame.setBounds(100, 100, 1280, 720);
        jFrame.setTitle("Game: " + length + "x" + height + ", " + difficulty);
        jFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        buildGui();
        
        jFrame.setVisible(true);
    }
}