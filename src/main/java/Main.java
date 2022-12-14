import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.util.*;

/**
 * @ClassName Main
 * @ClassDescription TODO
 * @Author zhangxiao
 * @Date 2022/12/14 00:11
 */
public class Main {

    // 定义可选的密码字符集
    private static final char[] NUMBER = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    private static final char[] UPPERCASE_LETTERS = new char[]{'A', 'B', 'C', 'D', 'E', 'G', 'F', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'W', 'Q', 'R', 'S', 'T', 'U', 'V', 'X', 'Y', 'Z'};
    private static final char[] LOWERCASE_LETTERS = new char[]{'a', 'b', 'c', 'd', 'e', 'g', 'f', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'w', 'q', 'r', 's', 't', 'u', 'v', 'x', 'y', 'z'};
    private static final char[] SYMBOL = new char[]{'^', '=', '.', '*', '#', '>', '@', '!', '?', '+', ':', '}', ')', ']', '-', ',', '~'};

    //
    private static final Random RANDOM = new Random();

    public static void main(String[] args) {
        callUI();
    }

    public static void callUI() {
        // 整体 UI 框架
        JFrame jf = new JFrame();
        // 设置窗口标题
        jf.setTitle("随机密码生成器");
        // 设置窗口宽高
        jf.setSize(460, 250);
        // 窗口居中显示
        jf.setLocationRelativeTo(null);
        // 禁止调整窗口大小
        jf.setResizable(false);

        // 设置布局
        FlowLayout fl = new FlowLayout();
        jf.setLayout(fl);  //给窗体设置为流式布局——从左到右然后从上到下排列自己写的组件顺序

        // 密码长度的标题及文本框
        JLabel pwLengthLabel = new JLabel("输入密码长度（8 ~ 100）");
        jf.add(pwLengthLabel);
        JTextField pwLengthTextField = new JTextField("16");
        Dimension dms = new Dimension(50, 20);
        pwLengthTextField.setPreferredSize(dms);
        jf.add(pwLengthTextField);

        // 复选框
        JCheckBox uppercaseLetter = new JCheckBox("大写");
        jf.add(uppercaseLetter);
        JCheckBox number = new JCheckBox("数字");
        jf.add(number);
        JCheckBox symbol = new JCheckBox("符号");
        jf.add(symbol);

        // 生成的密码文本
        JTextField randomPassword = new JTextField();
        randomPassword.setPreferredSize(new Dimension(380, 20));
        randomPassword.setHorizontalAlignment(JTextField.CENTER);
        jf.add(randomPassword);

        // 生成按钮
        JButton generateBtn = new JButton("生成");
        jf.add(generateBtn);
        // 复制按钮
        JButton copyBtn = new JButton("复制");
        jf.add(copyBtn);

        generateBtn.addActionListener(click -> {
            try {
                int pwLength = Integer.parseInt(pwLengthTextField.getText());
                if (pwLength >= 8 && pwLength <= 100) {
                    randomPassword.setText(generatePassword(uppercaseLetter.isSelected(), number.isSelected(), symbol.isSelected(), pwLength));
                } else {
                    randomPassword.setText("请输入长度为 8 ~100 的数字!");
                    pwLengthTextField.setText("");
                }
            } catch (NumberFormatException ex) {
                randomPassword.setText("请输入数字!");
                pwLengthTextField.setText("");
            }
        });

        copyBtn.addActionListener(click -> {
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(randomPassword.getText()), null);
        });

        // 窗口可见
        jf.setVisible(true);
    }

    /**
     * 生成随机密码
     *
     * @param isUppercase 是否大写
     * @param isNumber    是否数字
     * @param isSymbol    是否字符
     * @param length      生成密码的长度
     * @return 随机密码字符串
     */
    public static String generatePassword(boolean isUppercase, boolean isNumber, boolean isSymbol, int length) {
        //
        char[] AVAILABLE = new char[0];
        int oldLength = AVAILABLE.length;
        // 数组扩容
        AVAILABLE = Arrays.copyOf(LOWERCASE_LETTERS, LOWERCASE_LETTERS.length);
        // 拷贝数组
        System.arraycopy(LOWERCASE_LETTERS, 0, AVAILABLE, 0, LOWERCASE_LETTERS.length);

        if (isUppercase) {
            oldLength = AVAILABLE.length;
            AVAILABLE = Arrays.copyOf(AVAILABLE, UPPERCASE_LETTERS.length + AVAILABLE.length);
            System.arraycopy(UPPERCASE_LETTERS, 0, AVAILABLE, oldLength, UPPERCASE_LETTERS.length);
        }
        if (isNumber) {
            oldLength = AVAILABLE.length;
            AVAILABLE = Arrays.copyOf(AVAILABLE, NUMBER.length + AVAILABLE.length);
            System.arraycopy(NUMBER, 0, AVAILABLE, oldLength, NUMBER.length);
        }
        if (isSymbol) {
            oldLength = AVAILABLE.length;
            AVAILABLE = Arrays.copyOf(AVAILABLE, SYMBOL.length + AVAILABLE.length);
            System.arraycopy(SYMBOL, 0, AVAILABLE, oldLength, SYMBOL.length);
        }


        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(AVAILABLE[RANDOM.nextInt(AVAILABLE.length)]);
        }
        return sb.toString();
    }

/*    public static void triggerGenerationBtn(JButton generateBtn, JTextField randomPassword, JCheckBox uppercaseLetter,
                                            JCheckBox number, JCheckBox symbol, JTextField pwLengthTextField) {
        generateBtn.addActionListener(click -> {
            try {
                int pwLength = Integer.parseInt(pwLengthTextField.getText());
                if (pwLength >= 8 && pwLength <= 100) {
                    randomPassword.setText(generatePassword(uppercaseLetter.isSelected(), number.isSelected(), symbol.isSelected(), pwLength));
                } else {
                    randomPassword.setText("请输入长度为 8 ~100 的数字!");
                    pwLengthTextField.setText("");
                }
            } catch (NumberFormatException ex) {
                randomPassword.setText("请输入数字!");
                pwLengthTextField.setText("");
            }
        });
    }*/
}
