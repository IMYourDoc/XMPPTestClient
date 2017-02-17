/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imyourdoc.utils;

import org.jivesoftware.smack.Chat;

import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Presence.Type;

 

public class XmppManager {

     

    private static final int packetReplyTimeout = 500; // millis

     

    private String server;

    private int port;

     

    private ConnectionConfiguration config;

    private XMPPConnection connection;

 

    private ChatManager chatManager;

    private MessageListener messageListener;

     

    public XmppManager(String server, int port) {

        this.server = server;

        this.port = port;
        

  }

   

  public boolean init() throws XMPPException {

       
      try{
          
          
     System.out.println("Connecting..." + server + " on port " + port);

      //System.out.println(String.format("Initializing connection to server %1$s port %2$d", server, port));



      SmackConfiguration.setPacketReplyTimeout(packetReplyTimeout);

       

      config = new ConnectionConfiguration(server, port);

      
      //no SSL 
//      config.setSASLAuthenticationEnabled(false);
//      config.setSecurityMode(SecurityMode.disabled);

       
      //Yes SSL
       config.setSASLAuthenticationEnabled(true);
       config.setSecurityMode(SecurityMode.disabled);
      

      connection = new XMPPConnection(config);

      connection.connect();
      
       

      System.out.println("Connected: " + connection.isConnected());

       

      chatManager = connection.getChatManager();

      messageListener = new MyMessageListener();

      
      return true;
      }
      catch(Exception exp)
      {
          
          return false;
      }
       

  }

   

  public boolean performLogin(String username, String password) throws XMPPException {

      try{
      
          
      if (connection!=null && connection.isConnected()) {

          connection.login(username, password);
          System.out.println("Connection establashed");

      }
        return true;
      }
      
      catch(Exception exp)
      {
          return false;
      }

  }



  public void setStatus(boolean available, String status) {

       

      Presence.Type type = available? Type.available: Type.unavailable;

      Presence presence = new Presence(type);

       

      presence.setStatus(status);

      connection.sendPacket(presence);

       

  }

   

  public void destroy() {

      if (connection!=null && connection.isConnected()) {

          connection.disconnect();

      }

  }

   

  public void sendMessage(String message, String buddyJID) throws XMPPException {

      
      try{
      System.out.println(String.format("Sending mesage '%1$s' to user %2$s", message, buddyJID));

      Chat chat = chatManager.createChat(buddyJID, messageListener);

      chat.sendMessage(message);
      System.out.println("Message sent to " + buddyJID);
      
      }
      catch(Exception exp)
      {
          System.out.println("Error sending message: " + exp.toString());
      }
       
      
  }
  
  
  
  
  public void sendCustomMUCPacket(String MUC,String packet) throws XMPPException {

      
      try{
       //Message msg = new Message("mychattingroom@conference.10.10.1.105", Message.Type.groupchat);
       Message msg = new Message(MUC, Message.Type.groupchat);
      
       msg.setBody(packet);
   
            
       org.jivesoftware.smackx.muc.MultiUserChat muc = new  org.jivesoftware.smackx.muc.MultiUserChat(connection,MUC);
       muc.join(MUC);
       
       //Send String
       //muc.sendMessage(msg);
       
       
       //Send Packet
       connection.sendPacket(msg);
       
       
       
       //muc.sendMessage(m);
      // muc.sendMessage(msg);
       
       System.out.println("Sent MUC packet to " + MUC );

       
      
      }
      catch(Exception exp)
      {
          System.out.println("Error sending message: " + exp.toString());
      }
       
      
  }
  
  
  
//  public void sendGroupMessage(String groupName, String message) throws SmackException.NotConnectedException, XMPPException {
//    Roster roster = connection.getRoster();
//    RosterGroup rosterGroup = roster.getGroup(groupName);
//    Collection<RosterEntry> entries = rosterGroup.getEntries();
//    for (RosterEntry entry : entries) {
//       String user = entry.getName();
//       System.out.println(String.format("Sending message " + message + " to user " + user));
//        Chat chat = chatManager.createChat(user, messageListener);
//        chat.sendMessage(message);
//    }
//}
  
   

  public void createEntry(String user, String name) throws Exception {

      System.out.println(String.format("Creating entry for buddy '%1$s' with name %2$s", user, name));

      Roster roster = connection.getRoster();

      roster.createEntry(user, name, null);
      
      
      

  }

   

  class MyMessageListener implements MessageListener {



      @Override

      public void processMessage(Chat chat, Message message) {

          String from = message.getFrom();

          String body = message.getBody();

          System.out.println(String.format("Received message '%1$s' from %2$s", body, from));

      }

       

  }

   

}
