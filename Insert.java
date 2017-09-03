import java.util.*;
import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;		
import java.sql.*;

class Student implements Serializable
{
	String roll;
	String name;
	String gender;
	String fatherName;
	String course;
	int mDate;
	String mMonth;
	int mYear;
	String email;
	String pno;
	Set<String> hobbies;
	String adress;

	//Student c = new Student( name, gender, fatherName, course, email,pno, adress );
	public Student( String n, String g, String f, String c, String e, String p, String a)
	{
		roll = "A" + System.currentTimeMillis();
		name = n;
		gender = g;
		fatherName = f;
		course = c;
		email = e;
		pno = p;
		adress = a;
	}

	public Student( String n, String g, String f, String c, int d, String m, int y, String e, String p, String a, String... h)
	{
		roll = "A" + System.currentTimeMillis();
		name = n;
		gender = g;
		fatherName = f;
		course = c;
		mDate = d;
		mMonth = m;
		mYear = y;
		email = e;
		pno = p;
		adress = a;

		hobbies = new TreeSet<String>();
		for(String hb : h)
		{
			hobbies.add(hb);
		}
	}

	public String getRoll() {return roll;}
	public String getName() {return name;}
	public String getGender() {return gender;}
	public String getFatherName() {return fatherName;}
	public String getCourse() {return course;}
	public int getDate() {return mDate;}
	public String getMonth() {return mMonth;}
	public int getYear() {return mYear;}
	public String getEmail() {return email;}
	public String getPno() {return pno;}
	public Set<String> getHobbies() {return hobbies;}
	public String getAdress() {return adress;}

}

class MyWindow
{
	void resetScreen()
	{
		txtName.setText("");
		txtEmail.setText("");
		txtPno.setText("");
		txtFatherName.setText("");
		txtAdress.setText("");

		bg.clearSelection();
		cmbCourse.setSelectedIndex(0);
		cmdDate.setSelectedIndex(0);
		cmdMonth.setSelectedIndex(0);
		cmbYear.setSelectedIndex(0);

	    lstHobbies.clearSelection();

		chkAccept.setSelected(false);

	}

	class SaveClickHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent ae)
		{
			try
			{
				if( chkAccept.isSelected() )
				{
					String name = txtName.getText().trim().toUpperCase();
					String email = txtEmail.getText().trim().toUpperCase();
					String pno = txtPno.getText();
					String fatherName = txtFatherName.getText().trim().toUpperCase();
					String gender = ( (rdoMale.isSelected()) ? "M" : (rdoFemale.isSelected()?"F":null) );
					String course = (cmbCourse.getSelectedIndex()<=0) ? "" : (cmbCourse.getSelectedItem().toString());

					/*String sDate = (cmbDate.getSelectedIndex()<=0) ? "" : (cmbDate.getSelectedItem().toString());
					String sMonth = (cmbMonth.getSelectedIndex()<=0) ? "" : (cmbMonth.getSelectedItem().toString());
					String sYear = (cmbYear.getSelectedIndex()<=0) ? "" : (cmbYear.getSelectedItem().toString());
					*/
					Object[] sHobbies = lstHobbies.getSelectedValues();
					String adress = txtAdress.getText().trim().toUpperCase();
				
					if(name.length()==0)  throw new Exception("Missing Name..."); 
					if(gender.length()==0)  throw new Exception("Missing Gender..."); 
					if(fatherName.length()==0)  throw new Exception("Missing Father's Name..."); 
					if(course.length()==0)  throw new Exception("Missing Course..."); 
						
					/*if(sDate.length()==0)  throw new Exception("Select Date of Birth..."); 
					if(sMonth.length()==0)  throw new Exception("Select Date of Birth..."); 
					if(sYear.length()==0)  throw new Exception("Select Date of Birth..."); 
					*/
					if(email.length()==0)  throw new Exception("Missing Email..."); 
					//if(shobbies == null || shobbies.length == 0)  throw new Exception("Atleast 1 Hobby to be selected  ... ");						
						
					if(adress.length() <10)  throw new Exception("Address too short ..!! Please give a Valid address...");
				
					/*
					String[] hbs = new String[ hobbies.length ];
						System.arraycopy( hobbies, 0, hbs, 0 , hobbies.length );
						System.out.println( name + "," +  gender + "," +  course + "," +  address + "," +  hbs);
					*/

						Student c = new Student( name, gender, fatherName, course, email, pno, adress );
						ObjectOutputStream oos = new ObjectOutputStream( new BufferedOutputStream (new FileOutputStream( c.getRoll()+".txt" )) );
						oos.writeObject(c);
						oos.flush();
						oos.close();
						JOptionPane.showMessageDialog( frame, "Success : \n\nStudent Roll No. " + c.getRoll() );

						
						JDialog d = new JDialog(frame, "Details", true);

						JPanel p = new JPanel( new BorderLayout() );
						final JEditorPane ep = new JEditorPane();
						ep.setContentType("text/html");	
						ep.setText("<H1>"+c.getRoll()+"</H1>" + name+"<HR>"+gender+"<HR>"+fatherName+"<HR>" +course+"<HR>"+email +"<HR>"+pno+"<HR>"+adress );
						p.add(ep);

						JButton b = new JButton("Print");
						b.addActionListener
						(
							new ActionListener()
							{
								public void actionPerformed(ActionEvent ae)
								{
									try
									{
										ep.print();	
									}
									catch (Exception ex)
									{
									}
								}
							}
						);

						p.add( b, BorderLayout.SOUTH );	
						d.add(p);
						d.setVisible(true);
						d.setSize(500, 400);				

						resetScreen();
						return;
						
				}

				throw new Exception("You hava to accept terms & conditions");			
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog( null, "Problem : \n\n" + ex.getMessage() );
			}
		}
	}
	
	class ClearClickHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent ae)
		{
			resetScreen();
		}
	}
	
	class CancelClickHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent ae)
		{
			frame.dispose();
		}
	}
	
	class GenderSelectionHandler implements ItemListener
	{
		public void itemStateChanged(ItemEvent e )
		{
			if( rdoMale.isSelected() ) JOptionPane.showMessageDialog(null, "Male");
			if( rdoFemale.isSelected() ) JOptionPane.showMessageDialog(null, "Female");
		}
	}

	class MyFocus implements FocusListener
	{
		public void focusGained(FocusEvent fe)
		{
			txtName.setBackground( new Color( 255, 255, 0 ) );
			txtEmail.setBackground( new Color( 255, 255, 0 ) );
			txtPno.setBackground( new Color( 255, 255, 0 ) );
			txtFatherName.setBackground( new Color( 255, 255, 0 ) );
		}

		public void focusLost(FocusEvent fl)
		{
			txtName.setBackground( new Color( 0, 255, 0 ) );
			txtEmail.setBackground( new Color( 0, 255, 0 ) );
			txtPno.setBackground( new Color( 0, 255, 0 ) );
			txtFatherName.setBackground( new Color( 0, 255, 0 ) );
		}
	}

	class MyWindowHandler implements WindowListener
	{
			public void windowClosing(WindowEvent e) { JOptionPane.showMessageDialog(null, "Bye ... Visit Again");  }
			public void windowOpened(WindowEvent e) {  JOptionPane.showMessageDialog(null, "hi..welcome to Sophia...");  }
			public void windowIconified(WindowEvent e) {}
			public void windowDeiconified(WindowEvent e) {}
			public void windowClosed(WindowEvent e) { System.out.println("Closed"); }
			public void windowActivated(WindowEvent e) {}
			public void windowDeactivated(WindowEvent e) {}
	}

	public void addEventHandlers()
	{
		btnSave.addActionListener(new SaveClickHandler() );
		miSave.addActionListener(new SaveClickHandler() );

		btnClear.addActionListener(new ClearClickHandler() );
		miNew.addActionListener(new ClearClickHandler() );

		btnCancel.addActionListener(new CancelClickHandler() );
		miClose.addActionListener(new CancelClickHandler() );
		miExit.addActionListener(new CancelClickHandler() );

		rdoMale.addItemListener( new GenderSelectionHandler() );
		rdoFemale.addItemListener( new GenderSelectionHandler() );

		txtName.addFocusListener( new MyFocus() );
		txtEmail.addFocusListener( new MyFocus() );
		txtPno.addFocusListener( new MyFocus() );
		txtFatherName.addFocusListener( new MyFocus() );

		frame.addWindowListener( new MyWindowHandler() );
	}

	private JFrame frame;
	JLabel lblName, lblLogo, lblGender, lblCourse, lblEmail, lblPno, lblFatherName, lblHobbies, lblDob, lblAdress, lblTitle;
	JTextField txtName, txtEmail, txtPno, txtFatherName;
	JCheckBox chkAccept;

	ButtonGroup bg;    
	JRadioButton rdoMale, rdoFemale;
	JComboBox cmbCourse, cmdDate, cmdMonth, cmbYear;
	JList lstHobbies;
	
	JTextArea txtAdress;
	JButton btnSave, btnCancel, btnClear;
	JPanel panel;

	JMenuBar bar;
	JMenu mFile, mForm, mHelp;
	JMenuItem miNew, miOpen, miSave, miPrint, miClose, miLogin, miReg, miUpdate, miView, miExit, miAbout, miWebsite;
	JCheckBoxMenuItem cbAutoUpdate;

	GridBagLayout gbl;
	GridBagConstraints gbc;

	static final String[] courses = { "Pick One             ", "BA", "B.SC", "B.COM", "BBA", "BCA" };
	static final String[] date = { "1", "2", "3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29", "30", "31" };
	static final String[] month = { "Jan  ","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec" };
	static final String[] year = { "1990 ","1991","1992","1993","1994","1995","1996","1997","1998","1999","2000","2001","2002","2003" };
	static final String[] hobbies = { "Music" , "Painting", "Singing", "Dancing", "Reading", "Adventure ","Sports", "Cooking", "Writting " };

	public MyWindow()
	{
		frame = new JFrame("Insert : Enter your Information");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		panel = new JPanel();
		gbl = new GridBagLayout();
		gbc = new GridBagConstraints(); 

		lblName = new JLabel("Name : ");
		lblGender = new JLabel("Gender : ");
		lblCourse = new JLabel("Course : ");
		lblEmail = new JLabel("Email : ");
		lblPno = new JLabel("Mobile No.");
		lblFatherName  = new JLabel("Father's Name : ");
		lblHobbies = new JLabel("Hobbies : ");
		lblDob = new JLabel("Date of Birth : ");
		lblAdress = new JLabel("Address : ");
		lblTitle = new JLabel("           SOPHIA COLLEGE, AJMER ");
		lblLogo = new JLabel( new ImageIcon("logo.png") );

		chkAccept = new JCheckBox("Accept terms & conditions");

		txtName = new JTextField(30);
		txtEmail = new JTextField(30);
		txtPno = new JTextField(20);
		txtFatherName = new JTextField(30);

		rdoMale = new JRadioButton("M");
		rdoFemale = new JRadioButton("F");
		bg = new ButtonGroup();
		bg.add(rdoMale);
		bg.add(rdoFemale);

		cmbCourse = new JComboBox(courses);
		cmdDate = new JComboBox(date);
		cmdMonth = new JComboBox(month);
		cmbYear = new JComboBox(year);
		cmbCourse.setMaximumRowCount(4);
		cmbCourse.setEditable(true);

		//Dimension d = new Dimension( 200, 35 );
		//cmbCourse.setPreferredSize(d);

		lstHobbies = new JList(hobbies);
		lstHobbies.setPreferredSize(new Dimension( 90, 150 ) );
		
		txtAdress = new JTextArea(6, 35);
		txtAdress.setLineWrap(true);

		btnSave = new JButton("Save");
		btnCancel = new JButton("Cancel");
		btnClear = new JButton("Clear");

		bar = new JMenuBar();
		mFile = new JMenu("    File        "); 
		mForm = new JMenu("    Form      ");
		mHelp = new JMenu("    Help  ");

		miNew = new JMenuItem("New");
		miOpen = new JMenuItem("Open                              ");
		miSave = new JMenuItem("Save");
		miPrint = new JMenuItem("Print");
		miClose = new JMenuItem("Close");
		miLogin = new JMenuItem("Login");
		miReg = new JMenuItem("Registration                         ");
		miUpdate = new JMenuItem("Update");
		miView = new JMenuItem("View");
		miExit = new JMenuItem("Exit");
		miAbout = new JMenuItem("About");
		miWebsite = new JMenuItem("Website");

		cbAutoUpdate = new JCheckBoxMenuItem("Auto Update                     ");

		JComponent[]  comps = 
			{ txtName, txtFatherName, txtEmail, txtPno, rdoMale, rdoFemale, cmbCourse, lstHobbies, txtAdress, btnSave};
		for( JComponent comp : comps )
		{
			comp.setForeground( new Color( 0, 0, 255 ) );	
		}

	}

	void place(int x, int y, JComponent component, int anchor)		
	{
			gbc.insets = new Insets( 5,5,5,5 );			
			gbc.anchor = anchor;
			gbc.gridx=x;	
			gbc.gridy=y;	
			gbl.setConstraints(component, gbc);		
			panel.add(component);
	}

	void place(int x, int y, JComponent component)		
	{
			place(x,y,component, GridBagConstraints.NORTHWEST);
	}

	private void addControls()
	{
		panel.setLayout(gbl);
		panel.setBackground(new Color(255, 255, 200) );

		place(0,0,lblName);
		place(1,0,txtName);
		
		JPanel prdo = new JPanel();
		prdo.setBackground(new Color(255, 255, 200) );
		prdo.add(rdoMale);
		prdo.add(rdoFemale);

		place(0,1,lblGender);
		place(1,1,prdo);

		place(0,2,lblFatherName);
		place(1,2,txtFatherName);
		place(0,3,lblCourse);
		place(1,3,cmbCourse);
		
		JPanel pdob = new JPanel();
		pdob.setBackground(new Color(255, 255, 200) );
		pdob.add(cmdDate);
		pdob.add(cmdMonth);
		pdob.add(cmbYear);

		place(0,4,lblDob);
		place(1,4,pdob);

		place(0,5,lblEmail);
		place(1,5,txtEmail);
		place(0,6,lblPno);
		place(1,6,txtPno);
		place(0,7,lblHobbies);
		place(1,7,new JScrollPane(lstHobbies) );
		place(0,8,lblAdress);
		place(1,8,new JScrollPane(txtAdress) );
		place(1,9,chkAccept);
		
		frame.add(new JScrollPane(panel) );
	
		JPanel ptop = new JPanel();
		ptop.setLayout( new BorderLayout() );
		ptop.setBackground(new Color(255, 150, 100) );
		lblTitle.setFont(new Font("Arial Rounded MT Bold",Font.BOLD + Font.ITALIC , 60 ) );

		ptop.add(lblLogo, BorderLayout.WEST);
		ptop.add(lblTitle, BorderLayout.CENTER);
		frame.add(ptop, BorderLayout.NORTH);

		JPanel pbottom = new JPanel();
		pbottom.setBackground( new Color(100, 100, 10) );
		pbottom.add(btnClear);
		pbottom.add(btnSave);
		pbottom.add(btnCancel);

		frame.add(pbottom, BorderLayout.SOUTH);
		
		mFile.add(miNew);
		mFile.add(miOpen);
		mFile.add(miSave);
		mFile.add(miPrint);
		mFile.add(miClose);
		
		mForm.add(miLogin);
		mForm.add(miReg);
		mForm.add(miView);
		mForm.add(miUpdate);
		mForm.add(miExit);
		
		mHelp.add(miAbout);
		mHelp.add(miWebsite);
		mHelp.add(cbAutoUpdate);

		bar.add(mFile);
		bar.add(mForm);
		bar.add(mHelp);

		frame.setJMenuBar(bar);	//frame.setJMenuBar(bar);

	}

	public void launch()
	{
		addControls();
		addEventHandlers();
		frame.setExtendedState(6);
		frame.setVisible(true);
	}
}

public class Insert
{
	static
	{
		try
		{
			UIManager.setLookAndFeel(  UIManager.getSystemLookAndFeelClassName()  );		
			//Class.forName( Database.driver );
		}
		catch (Exception ex)
		{
			System.out.println(ex.getMessage());
			System.exit(0);
		}
	}
	public static void main(String[] args) 
	{
		new MyWindow().launch();
	}
}
