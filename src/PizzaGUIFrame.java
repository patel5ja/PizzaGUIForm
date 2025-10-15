
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

public class PizzaGUIFrame extends JFrame {


    JPanel mainPnl;
    JPanel radioPnl;
    JPanel checkPnl;
    JPanel comboPnl;
    JPanel controlPnl;
    JPanel receiptPnl;
    JTextArea receiptArea;


    JRadioButton thin;
    JRadioButton regular;
    JRadioButton deepDish;

    JComboBox<String> sizeCB;;

    JCheckBox spinach;
    JCheckBox onions;
    JCheckBox pepper;
    JCheckBox pineapple;
    JCheckBox olives;
    JCheckBox jalapeno;
    JCheckBox cheese;

    JButton quitBtn;
    JButton orderBtn;
    JButton clearBtn;


    public PizzaGUIFrame() {

        super("Pizza Form");

        mainPnl = new JPanel();
        mainPnl.setLayout(new GridLayout(4, 1));

        createRadioPanel();
        mainPnl.add(radioPnl);

        createCheckPanel();
        mainPnl.add(checkPnl);

        createControlPanel();
        mainPnl.add(controlPnl);

        createComboPanel();
        mainPnl.add(comboPnl);

        createReceiptPanel();
        mainPnl.add(receiptPnl);

        add(mainPnl);
        setSize(800, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);



    }


    private void createComboPanel() {

        comboPnl = new JPanel();
        comboPnl.setBorder(new TitledBorder(new EtchedBorder(), "Size"));

        sizeCB = new JComboBox();
        sizeCB.addItem("Small");
        sizeCB.addItem("Medium");
        sizeCB.addItem("Large");
        sizeCB.addItem("Super");
        comboPnl.add(sizeCB);

    }



    private void createRadioPanel() {
        radioPnl = new JPanel();
        radioPnl.setLayout(new GridLayout(4, 1));
        radioPnl.setBorder(new TitledBorder(new EtchedBorder(), "Crust"));

        thin = new JRadioButton("Thin");
        regular = new JRadioButton("Regular");
        deepDish = new JRadioButton("Deep-dish");

        radioPnl.add(thin);
        radioPnl.add(regular);
        radioPnl.add(deepDish);

        ButtonGroup group = new ButtonGroup();
        group.add(thin);
        group.add(regular);
        group.add(deepDish);

    }

    private void createCheckPanel() {
        checkPnl = new JPanel();
        checkPnl.setLayout(new GridLayout(2, 4));
        checkPnl.setBorder(new TitledBorder(new EtchedBorder(), "Toppings"));

        spinach = new JCheckBox("Spinach");
        onions = new JCheckBox("Onions");
        pepper = new JCheckBox("Pepper");
        pineapple = new JCheckBox("Pineapple");
        olives = new JCheckBox("Olives");
        jalapeno = new JCheckBox("Jalapeno");
        cheese = new JCheckBox("Cheese");

        checkPnl.add(spinach);
        checkPnl.add(onions);
        checkPnl.add(pepper);
        checkPnl.add(pineapple);
        checkPnl.add(olives);
        checkPnl.add(jalapeno);
        checkPnl.add(cheese);

    }


    private void createReceiptPanel() {
        receiptPnl = new JPanel(new BorderLayout());
        receiptPnl.setBorder(new TitledBorder(new EtchedBorder(), "Order Receipt"));
        receiptArea = new JTextArea();
        receiptArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(receiptArea);
        receiptPnl.add(scrollPane, BorderLayout.CENTER);
    }

    private void createControlPanel() {
        controlPnl = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        controlPnl.setBorder(new TitledBorder(new EtchedBorder(), "Controls"));

        orderBtn = new JButton("Order");
        clearBtn = new JButton("Clear");
        quitBtn  = new JButton("Quit");

        orderBtn.addActionListener(e -> generateReceipt());
        clearBtn.addActionListener(ae -> clearForm());
        quitBtn.addActionListener(e -> {
            if (JOptionPane.showConfirmDialog(this, "Quit?") == JOptionPane.YES_OPTION)
                System.exit(0);
        });

        controlPnl.add(orderBtn);
        controlPnl.add(clearBtn);
        controlPnl.add(quitBtn);
    }

    private void clearForm() {
        thin.setSelected(false);
        regular.setSelected(false);
        deepDish.setSelected(false);
        sizeCB.setSelectedIndex(0);

        spinach.setSelected(false);
        onions.setSelected(false);
        pepper.setSelected(false);
        pineapple.setSelected(false);
        olives.setSelected(false);
        jalapeno.setSelected(false);

        receiptArea.setText("");
    }


    private void generateReceipt() {
        String crust = "";
        if (thin.isSelected()) crust = "Thin";
        else if (regular.isSelected()) crust = "Regular";
        else if (deepDish.isSelected()) crust = "Deep-dish";

        if (crust.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a crust type.");
            return;
        }

        String size = (String) sizeCB.getSelectedItem();
        double basePrice = switch (size) {
            case "Small" -> 8.00;
            case "Medium" -> 12.00;
            case "Large" -> 16.00;
            case "Super" -> 20.00;
            default -> 0.00;
        };

        java.util.List<String> selectedToppings = new java.util.ArrayList<>();
        if (spinach.isSelected()) selectedToppings.add("Spinach");
        if (onions.isSelected()) selectedToppings.add("Onions");
        if (pepper.isSelected()) selectedToppings.add("Pepper");
        if (pineapple.isSelected()) selectedToppings.add("Pineapple");
        if (olives.isSelected()) selectedToppings.add("Olives");
        if (jalapeno.isSelected()) selectedToppings.add("Jalapeno");
        if (cheese.isSelected()) selectedToppings.add("Cheese");

        if (selectedToppings.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select at least one topping.");
            return;
        }

        double toppingCost = selectedToppings.size();
        double subtotal = basePrice + toppingCost;
        double tax = subtotal * 0.07;
        double total = subtotal + tax;

        receiptArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        final String TOP =  "==========================================";
        final String SEP =  "------------------------------------------";
        final double TOPPING_PRICE = 1.00;

        StringBuilder r = new StringBuilder();
        r.append(TOP).append("\n");

        r.append(String.format("%-28s %10s%n", crust + " / " + size, String.format("$%.2f", basePrice)));


        for (String t : selectedToppings) {
            r.append(String.format("%-28s %10s%n", t, String.format("$%.2f", TOPPING_PRICE)));
        }

        r.append("\n");
        r.append(String.format("%-28s %10s%n", "Sub-total:", String.format("$%.2f", subtotal)));
        r.append(String.format("%-28s %10s%n", "Tax:", String.format("$%.2f", tax)));
        r.append(SEP).append("\n");
        r.append(String.format("%-28s %10s%n", "Total:", String.format("$%.2f", total)));
        r.append(TOP).append("\n");

        receiptArea.setText(r.toString());
        receiptArea.setCaretPosition(0);
    }



}