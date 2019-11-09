import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.awt.event.ActionEvent;


public class Program {

	private JFrame frame;
	private JLabel lblOriginalData;
	private JLabel lblHammingCode;
	private JTextField originalData;
	private JTextField hammingCode;
	private JTextField receivedData;
	private JButton Make;
	private JLabel lblNewLabel;
	private JButton Check;
	private JLabel wrongAt;
	private JLabel fixedCode;

	/**
	 * Launch the application.
	 */
	 String answer;
	private static JTextField errorBox;
	private static JTextField fixedBox;
	 static void print(int ar[]) 
	    { 
	        for (int i = 1; i < ar.length; i++) { 
	            System.out.print(ar[i]); 
	        } 
	        System.out.println(); 
	    } 
	    static int[] calculation(int[] ar, int r) 
	    { 
	        for (int i = 0; i < r; i++) { 
	            int x = (int)Math.pow(2, i); 
	            for (int j = 1; j < ar.length; j++) { 
	                if (((j >> i) & 1) == 1) { 
	                    if (x != j) 
	                        ar[x] = ar[x] ^ ar[j]; 
	                } 
	            } 
	        } 
	  
	        return ar; 
	    } 
	static int[] generateCode(String str, int M, int r) 
    { 
        int[] ar = new int[r + M + 1]; 
        int j = 0; 
        for (int i = 1; i < ar.length; i++) { 
            if ((Math.ceil(Math.log(i) / Math.log(2)) 
                 - Math.floor(Math.log(i) / Math.log(2))) 
                == 0) { 

                ar[i] = 0; 
            } 
            else { 
                ar[i] = (int)(str.charAt(j) - '0'); 
                j++; 
            } 
        } 
        return ar; 
    } 
	static void Receive(int a[], int parity_count) {
		
		int power;
		
		int parity[] = new int[parity_count];
		
		String syndrome = new String();
		
		for(power=0 ; power < parity_count ; power++) {
			
			for(int i=0 ; i < a.length ; i++) {
				
				int k = i+1;
				String s = Integer.toBinaryString(k);
				int bit = ((Integer.parseInt(s))/((int) Math.pow(10, power)))%10;
				if(bit == 1) {
					if(a[i] == 1) {
						parity[power] = (parity[power]+1)%2;
					}
				}
			}
			syndrome = parity[power] + syndrome;
		}
		
		int error_location = Integer.parseInt(syndrome, 2);
		if(error_location != 0) {
			errorBox.setText(Integer.toString(error_location));
			a[error_location-1] = (a[error_location-1]+1)%2;

			fixedBox.setText(Arrays.toString(a).replaceAll("\\[|\\]|,|\\s", ""));
			System.out.println();
		}
		else {
			errorBox.setText("No Error");
			fixedBox.setText(Arrays.toString(a).replaceAll("\\[|\\]|,|\\s", ""));
		}

	}

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Program window = new Program();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Program() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.frame = new JFrame();
		this.frame.setBounds(100, 100, 453, 340);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.getContentPane().setLayout(null);
		
		this.lblOriginalData = new JLabel("Original Data: ");
		this.lblOriginalData.setBounds(30, 25, 83, 15);
		this.frame.getContentPane().add(this.lblOriginalData);
		
		this.lblHammingCode = new JLabel("Hamming Code:");
		this.lblHammingCode.setBounds(30, 50, 103, 15);
		this.frame.getContentPane().add(this.lblHammingCode);
		
		this.originalData = new JTextField();
		this.originalData.setBounds(166, 22, 241, 21);
		this.frame.getContentPane().add(this.originalData);
		this.originalData.setColumns(10);
		
		this.hammingCode = new JTextField();
		this.hammingCode.setColumns(10);
		this.hammingCode.setBounds(166, 50, 241, 21);
		this.frame.getContentPane().add(this.hammingCode);
		
		this.receivedData = new JTextField();
		this.receivedData.setColumns(10);
		this.receivedData.setBounds(166, 148, 241, 21);
		this.frame.getContentPane().add(this.receivedData);
		
		this.Make = new JButton("Make Hamming Code");
		this.Make.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String str = originalData.getText();
		        int M = str.length(); 
		        int r = 1; 
		  
		        while (Math.pow(2, r) < (M + r + 1)) { 
		            r++; 
		        } 
		        int[] ar = generateCode(str, M, r); 
		  
		        ar = calculation(ar, r);
		        int [] answer = new int [M + r];
		        for(int i =1; i < ar.length; i++) {
		         	answer[i-1] = ar[i];
		        }
		        hammingCode.setText(Arrays.toString(answer).replaceAll("\\[|\\]|,|\\s", ""));
		        
				
			}
		});
		this.Make.setBounds(113, 100, 192, 21);
		this.frame.getContentPane().add(this.Make);
		
		this.lblNewLabel = new JLabel("Received Data:");
		this.lblNewLabel.setBounds(30, 151, 103, 15);
		this.frame.getContentPane().add(this.lblNewLabel);
		
		this.Check = new JButton("Check Data");
		this.Check.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String received = receivedData.getText();
				String[] splited = received.split("");
				int size = received.length();
				int r = 1;
				while (Math.pow(2, r) < (size + r + 1)) { 

		            r++; 
		        } 
				int [] arr = new int [size];
				for(int i = 0; i < size; i++) {
					arr[i] = Integer.parseInt(splited[i]);
				}
				Receive(arr, r);
				
			}
		});
		this.Check.setBounds(113, 193, 192, 21);
		this.frame.getContentPane().add(this.Check);
		
		this.wrongAt = new JLabel("Error Point: ");
		this.wrongAt.setBounds(118, 242, 211, 21);
		this.frame.getContentPane().add(this.wrongAt);
		
		this.fixedCode = new JLabel("Fixed Code: ");
		this.fixedCode.setBounds(113, 273, 114, 18);
		this.frame.getContentPane().add(this.fixedCode);
		
		this.errorBox = new JTextField();
		this.errorBox.setBounds(235, 242, 116, 21);
		this.frame.getContentPane().add(this.errorBox);
		this.errorBox.setColumns(10);
		
		this.fixedBox = new JTextField();
		this.fixedBox.setBounds(235, 273, 116, 21);
		this.frame.getContentPane().add(this.fixedBox);
		this.fixedBox.setColumns(10);
	}
}
