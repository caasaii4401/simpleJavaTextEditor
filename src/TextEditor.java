import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class TextEditor extends JFrame {

    private JTextArea textArea;
    private JTextField statusField;
    private File currentFile;

    public TextEditor() {
        initUI();
    }

    private void initUI() {
        setTitle("Text Editor");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // 创建菜单栏
        JMenuBar menubar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem openItem = new JMenuItem("Open");
        openItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openFile();
            }
        });
        JMenuItem saveItem = new JMenuItem("Save");
        saveItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveFile();
            }
        });
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(exitItem);
        menubar.add(fileMenu);
        setJMenuBar(menubar);

        // 创建工具栏
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton cutButton = new JButton("Cut");
        cutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textArea.cut();
            }
        });
        JButton copyButton = new JButton("Copy");
        copyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textArea.copy();
            }
        });
        JButton pasteButton = new JButton("Paste");
        pasteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textArea.paste();
            }
        });
        toolbar.add(cutButton);
        toolbar.add(copyButton);
        toolbar.add(pasteButton);
        add(toolbar, BorderLayout.NORTH);

        // 创建编辑区域
        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        // 创建状态栏
        statusField = new JTextField("Ready");
        statusField.setEditable(false);
        add(statusField, BorderLayout.SOUTH);

        // 设置窗口居中显示
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
    }

    // 打开文件
    private void openFile() {
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(chooser.getSelectedFile()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                    sb.append("\n");
                }
                reader.close();

                textArea.setText(sb.toString());
                currentFile = chooser.getSelectedFile();
                statusField.setText(currentFile.getName() + " opened");
            } catch (IOException e) {
                statusField.setText("Failed to open file");
            }
        }
    }

    // 保存文件
    private void saveFile() {
        if (currentFile != null) {
            try {
                FileWriter writer = new FileWriter(currentFile);
                writer.write(textArea.getText());
                writer.close();
                statusField.setText(currentFile.getName() + " saved");
            } catch (IOException e) {
                statusField.setText("Failed to save file");
            }
        } else {
            JFileChooser chooser = new JFileChooser();
            int result = chooser.showSaveDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    FileWriter writer = new FileWriter(chooser.getSelectedFile());
                    writer.write(textArea.getText());
                    writer.close();
                    currentFile = chooser.getSelectedFile();
                    statusField.setText(currentFile.getName() + " saved");
                } catch (IOException e) {
                    statusField.setText("Failed to save file");
                }
            }
        }
    }

}
