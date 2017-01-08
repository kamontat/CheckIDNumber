package test;

import com.kamontat.code.file.FileChooser;

import javax.swing.*;

/**
 * @author kamontat
 * @version 1.0
 * @since 10/24/2016 AD - 2:40 PM
 */
public class test {
	public static void main(String[] args) {
		JFrame jFrame = new JFrame();
		JPanel a = new JPanel();
		JButton save = new JButton("Save");
		JButton open = new JButton("Open");
		
		
		FileChooser chooser = new FileChooser();
		open.addActionListener(e -> System.out.println(chooser.open(open)));
		
		save.addActionListener(e -> System.out.println(chooser.save(save)));
		
		
		a.add(save);
		a.add(open);
		jFrame.setContentPane(a);
		jFrame.pack();
		jFrame.setVisible(true);
		jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		
	}
}
