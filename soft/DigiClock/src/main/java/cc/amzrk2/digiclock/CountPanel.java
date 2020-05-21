package cc.amzrk2.digiclock;
import javax.swing.*;
import java.awt.*;

public class CountPanel extends JPanel
{
	private int hashCode;
	public JLabel content_1;
	public JLabel content_2;
	public JButton deleteButton;

	private CountPanel(int hashCode)
	{
		this.hashCode = hashCode;
	}

	public CountPanel(TimeManager timeManager)
	{
		super();

		GridBagLayout layout;
		GridBagConstraints constraints;

		layout = new GridBagLayout();
		constraints = new GridBagConstraints();
		this.setLayout(layout);
		constraints.fill = GridBagConstraints.BOTH;
		constraints.weightx = 0.0;

		this.content_1 = new JLabel("content1");
		this.content_2 = new JLabel("content2");
		this.deleteButton = new JButton("删除");

                this.content_1.setFont(DigiClock.fontSm);
                this.content_2.setFont(DigiClock.fontSm);
                this.deleteButton.setFont(DigiClock.fontSm);

		this.deleteButton.addActionListener(
				event -> timeManager.removeCount(this.hashCode)
		);

		// content_1
		constraints.gridheight = 1;
		constraints.gridwidth = 2;
		constraints.gridx = 0;
		constraints.gridy = 0;
		layout.setConstraints(this.content_1, constraints);
		this.add(this.content_1);
		// deleteButton
		constraints.gridheight = 1;
		constraints.gridwidth = 1;
		constraints.gridx = 2;
		constraints.gridy = 0;
		layout.setConstraints(this.deleteButton, constraints);
		this.add(this.deleteButton);
		// content_2
		constraints.gridheight = 1;
		constraints.gridwidth = 2;
		constraints.gridx = 0;
		constraints.gridy = 1;
		layout.setConstraints(this.content_2, constraints);
		this.add(this.content_2);

		// this.setVisible(true);
	}

	public void setHashCode(int hashCode)
	{
		this.hashCode = hashCode;
	}

	@Override
	public Dimension getPreferredSize()
	{
		// 强制大小。
		return new Dimension(180, 70);
	}

	public static CountPanel getInstanceCompare(int hashCode)
	{
		return new CountPanel(hashCode);
	}

        @Override
        public int hashCode()
        {
            return this.hashCode;
        }
        
        @Override
        public boolean equals(Object obj)
        {
            return (obj instanceof CountPanel)&& this.hashCode == obj.hashCode();
        }
}
