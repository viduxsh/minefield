import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;


public class Main extends JFrame{
    private static JFrame jFrame = new JFrame();
    private static JPanel north;
    private static JButton easy;
    private static JButton medium;
    private static JButton hard;
    private static JPanel west;
    private static int length = 0;
    private static int height = 0;
    private static Label lengthLabel;
    private static JTextArea lengthTextArea;
    private static Label heightLabel;
    private static JTextArea heightTextArea;
    private static JButton setField;
    private static JPanel center;
    private static Label[] field;
    private static JPanel east;
    private static int lifes = 0;
    private static int hints = 0;
    private static Label statsLifes;
    private static Label statsHints;
    private static JPanel south;
    private static String difficulty = "";
    private static Label resume;
    private static JButton start;

    
    public static void Gui(){
        jFrame.setBounds(100, 100, 1280, 720);
        jFrame.setTitle("Minefield");
        jFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        buildGui();
        
        jFrame.setVisible(true);
    }

    private static void northPanel(){
        north = new JPanel();
        north.setBorder(new TitledBorder("Difficulty"));

        easy = new JButton("Easy");
        easy.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                difficulty = "easy";
                lifes = 6;
                hints = 6;
                statsLifes.setText("Lifes: 6");
                statsHints.setText("Hints: 6");
                resume.setText("Choosen difficulty: easy");
            }
        });
        
        medium = new JButton("Medium");
        medium.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                difficulty = "medium";
                lifes = 4;
                hints = 4;
                statsLifes.setText("Lifes: 4");
                statsHints.setText("Hints: 4");
                resume.setText("Choosen difficulty: medium");
            }
        });
        
        hard = new JButton("Hard");
        hard.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                difficulty = "hard";
                lifes = 2;
                hints = 2;
                statsLifes.setText("Lifes: 2");
                statsHints.setText("Hints: 2");
                resume.setText("Choosen difficulty: hard");
            }
        });

        north.add(easy);
        north.add(medium);
        north.add(hard);
    }

    private static void westPanel(){
        west = new JPanel(new GridLayout(3, 2));
        west.setBorder(new TitledBorder("Field"));

        lengthLabel = new Label();
        lengthLabel.setText("Length: ");
        lengthTextArea = new JTextArea("Insert the length\nof the field\n(number)");
        heightLabel = new Label();
        heightLabel.setText("Height: ");
        heightTextArea = new JTextArea("Insert the height\nof the field\n(number)");
        setField = new JButton("Set field");
        setField.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try{
                    length = Integer.parseInt(lengthTextArea.getText());
                    try{
                        height = Integer.parseInt(heightTextArea.getText());
                        if((height <= 0 && height < 48) || (length <= 0 && length < 48)){
                            JOptionPane.showMessageDialog(null, "Invalid numbers");
                        }
                        else{
                            updateCenterPanel();
                        }
                    }
                    catch(Exception exc){
                        height = 0;
                        JOptionPane.showMessageDialog(null, "Height: this is not a number");
                    }
                }
                catch(Exception exc){
                    length = 0;
                    JOptionPane.showMessageDialog(null, "Length: this is not a number");
                }
            }
        });

        west.add(lengthLabel);
        west.add(lengthTextArea);
        west.add(heightLabel);
        west.add(heightTextArea);
        west.add(setField);
    }

    private static void updateCenterPanel(){
        boolean c = true;
        for(int i = 0; i < height; i++){
            String s = "";
            for(int j = 0; j < length; j++){
                if(c == true){
                    //s += "⬜";
                    s += "o";
                    c = false;
                }
                else{
                    //s += "⬛";
                    s += "x";
                    c = true;
                }
            }
            field[i].setText(s);
            center.add(field[i]);
            c = (c == true)?false:true;
        }
        for(int z = height; z < 48; z++){
            field[z].setText("");
        }
    }
    
    private static void centerPanel(){
        center = new JPanel(new GridLayout(48, 1));
        center.setBorder(new TitledBorder("Preview"));

        field = new Label[48];
        boolean c = true;
        for(int i = 0; i < 48; i++){
            String s = "";
            field[i] = new Label();
            for(int j = 0; j < 48; j++){
                if(c == true){
                    //s += "⬜";
                    s += "o";
                    c = false;
                }
                else{
                    //s += "⬛";
                    s += "x";
                    c = true;
                }
            }
            field[i].setText(s);
            center.add(field[i]);
            c = (c == true)?false:true;
        }
    }
    
    private static void eastPanel(){
        east = new JPanel(new GridLayout(3, 1));
        east.setBorder(new TitledBorder("Stats"));

        statsLifes = new Label();
        statsLifes.setText("Lifes: /");
        statsHints = new Label();
        statsHints.setText("Hints: /");

        east.add(statsLifes);
        east.add(statsHints);
    }
    
    private static void southPanel(){
        south = new JPanel(new GridLayout(2, 1));
        south.setBorder(new TitledBorder("Resume"));
        
        resume = new Label();
        resume.setText("Choosen difficulty: ________");
        
        start = new JButton("Play");
        start.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(difficulty != "" && length != 0 && height != 0){
                    System.out.println("Start game: h" + height + ", l" + length + " d" + difficulty + " l" + lifes + " h" + hints);
                    Board game = new Board(height, length, difficulty, lifes, hints);
                    game.init();
                    game.print();
                    game.play();
                }
                else if(difficulty == ""){
                    JOptionPane.showMessageDialog(null, "Difficulty not choosen");
                }
                else{
                    JOptionPane.showMessageDialog(null, "Field not setted");
                }
            }
        });

        south.add(resume);
        south.add(start);
    }
    
    private static void buildGui(){
        // North panel
        northPanel();
        jFrame.getContentPane().add(north, BorderLayout.NORTH);

        // West panel
        westPanel();
        jFrame.getContentPane().add(west, BorderLayout.WEST);

        // Center panel
        centerPanel();
        jFrame.getContentPane().add(center, BorderLayout.CENTER);

        // East panel
        eastPanel();
        jFrame.getContentPane().add(east, BorderLayout.EAST);

        // South panel
        southPanel();
        jFrame.getContentPane().add(south, BorderLayout.SOUTH);
    }
    
    public static void main(String[] args) {
        Gui();
    }
}
