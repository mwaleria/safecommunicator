package pl.mwaleria.safecommunicator.client.gui;

import javax.swing.ImageIcon;

public class BlinkingIcon implements Runnable {

	private CommunicatorForm communicatorForm;

	private boolean stop = false;
	private ImageIcon normalIcon;
	private ImageIcon newMessageIcon;
	private ImageIcon current;

	public BlinkingIcon(CommunicatorForm communicatorForm) {
		this.communicatorForm = communicatorForm;
		normalIcon = new ImageIcon(getClass().getResource("normal.png"));
		newMessageIcon = new ImageIcon(getClass().getResource("new_message.png"));
	}

	@Override
	public void run() {
		stop = false;
		current = normalIcon;
		while (true) {

			try {
				Thread.sleep(200);
				if (current == normalIcon) {
					current = newMessageIcon;
				} else {
					current = normalIcon;
				}
				communicatorForm.setIconImage(current.getImage());
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
			if (stop) {
				communicatorForm.setIconImage(normalIcon.getImage());
				break;
			}

		}
	}
}
