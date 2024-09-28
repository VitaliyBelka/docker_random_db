import javax.swing.*;
import java.awt.*;

public class NumberInputWindow {
    private JFrame frame;
    private JTextField textField;
    private int number;

    public NumberInputWindow() {
        // Создаем основное окно
        frame = new JFrame("Введите число");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 150);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new FlowLayout());

        // Создаем текстовое поле для ввода числа
        textField = new JTextField(10);
        JButton button = new JButton("Отправить");

        // Добавляем обработчик события для кнопки
        button.addActionListener(e -> {
            // Получаем текст из текстового поля
            String inputText = textField.getText();
            try {
                number = Integer.parseInt(inputText);
                frame.dispose(); // Закрываем окно после ввода
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Введите корректное число.", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Добавляем компоненты в окно
        frame.add(new JLabel("Введите число:"));
        frame.add(textField);
        frame.add(button);

        // Отображаем окно
        frame.setVisible(true);
    }

    public int getNumber() {
        return number;
    }
}