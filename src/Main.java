import java.awt.*;

public class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                TextEditor editor = new TextEditor();
                editor.setVisible(true);
            }
        });
    }
}