package examples.regresionMultiple;

import jade.core.AID;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class regresionMultipleGui extends JFrame {
    private regresionMultipleAgent myAgent;
	
	private JTextField prediccionXField, prediccionZField, metodoField;
	
	regresionMultipleGui(regresionMultipleAgent a) {
		super(a.getLocalName());
		
		myAgent = a;
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(3, 3));
		p.add(new JLabel("Prediccion X:"));
		prediccionXField = new JTextField(15);
		p.add(prediccionXField);
		p.add(new JLabel("Prediccion Z:"));
		prediccionZField = new JTextField(15);
		p.add(prediccionZField);
		p.add(new JLabel("Metodo:"));
		metodoField = new JTextField(15);
		p.add(metodoField);
		getContentPane().add(p, BorderLayout.CENTER);
		
		JButton addButton = new JButton("Add");
		addButton.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				try {
					String prediccionX = prediccionXField.getText().trim();
					String prediccionZ = prediccionZField.getText().trim();
					String metodo =  metodoField.getText().toLowerCase().trim();
					myAgent.agregarPrediccion(Double.parseDouble(prediccionX), Double.parseDouble(prediccionZ));
					myAgent.agregarMetodoRegresion(metodo);
					prediccionXField.setText("");
					prediccionZField.setText("");
					metodoField.setText("");
				}
				catch (Exception e) {
					JOptionPane.showMessageDialog(regresionMultipleGui.this, "Invalid values. "+e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); 
				}
			}
		} );
		p = new JPanel();
		p.add(addButton);
		getContentPane().add(p, BorderLayout.SOUTH);
		
		// Make the agent terminate when the user closes 
		// the GUI using the button on the upper right corner	
		addWindowListener(new	WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				myAgent.doDelete();
			}
		} );
		
		setResizable(false);
	}
	
	public void showGui() {
		pack();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int centerX = (int)screenSize.getWidth() / 2;
		int centerY = (int)screenSize.getHeight() / 2;
		setLocation(centerX - getWidth() / 2, centerY - getHeight() / 2);
		super.setVisible(true);
	}	
}
