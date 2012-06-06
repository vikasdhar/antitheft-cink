package com.dotcink.android.telephony;

/**
 * An SMS message item.
 * @author dot.cink
 *
 */
public class SmsMessage {

	private String sender;
	private String messageBody;
	
	/**
	 * 
	 * @param sender The sender address (phone number).
	 * @param messageBody The message body .
	 */
	public SmsMessage(String sender, String messageBody) {
		this.sender = sender;
		this.messageBody = messageBody;
	}

	/**
	 * Get the sender address of this SMS message.
	 * @return sender address of this SMS message.
	 */
	public String getSender() {
		return sender;
	}
	
	/**
	 * Get the message body of this SMS message.
	 * @return message body of this SMS message.
	 */
	public String getMessageBody() {
		return messageBody;
	}
	
}
