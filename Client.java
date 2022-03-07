/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ada20;

import java.awt.AWTException;
import java.awt.Font;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Alvie Zhang
 */
public class Client extends javax.swing.JFrame {

    /**
     * Creates new form Client
     */
    public static final String ADDRESS = "LocalHost";
    public static final int PORT = 2333;   
    boolean fileAdded = false;
    boolean isConnected = false;
    Socket socket;
    PrintWriter pw; // output stream to server
    BufferedReader br; // input stream from server
    
    ArrayList<String> otherUsers = new ArrayList<String>();
    ArrayList<String> file = new ArrayList();
    ArrayList fileStream = new ArrayList();
    public void startClient() {
        /*This method is used for after a client connected*/
        try {
            socket = new Socket(ADDRESS, PORT);
            String user = username.getText();
            clientChat.setFont(new Font("Serif", Font.ITALIC, 21));
            clientChat.append("Client " + user + " Connected!\n");
            isConnected = true;
            pw = new PrintWriter(socket.getOutputStream(), true);
            pw.println(user);
            pw.flush();
        } catch (Exception ex) {
            clientChat.append("Error!");
        }
    }

    public Client() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        username = new javax.swing.JTextField();
        connectButton = new javax.swing.JButton();
        disconnectButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        clientChat = new javax.swing.JTextArea();
        clientInput = new javax.swing.JTextField();
        sendButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        onlineUser = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Address:");

        jLabel2.setText("Port:");

        jLabel3.setText("Username:");

        connectButton.setText("Connect");
        connectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connectButtonActionPerformed(evt);
            }
        });

        disconnectButton.setText("Disconnect");
        disconnectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                disconnectButtonActionPerformed(evt);
            }
        });

        clientChat.setColumns(20);
        clientChat.setRows(5);
        jScrollPane1.setViewportView(clientChat);

        sendButton.setText("Send");
        sendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendButtonActionPerformed(evt);
            }
        });

        onlineUser.setColumns(20);
        onlineUser.setRows(5);
        jScrollPane2.setViewportView(onlineUser);

        jLabel4.setText("Online Users");

        jLabel5.setText("LocalHost");

        jLabel6.setText("2333");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel5)
                                .addGap(43, 43, 43)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel6))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(username, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(48, 48, 48)
                                .addComponent(connectButton, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(62, 62, 62)
                        .addComponent(disconnectButton, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(101, 101, 101)
                        .addComponent(jLabel4))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 493, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(clientInput, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(sendButton, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(disconnectButton, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel3)
                                .addComponent(username, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(connectButton))))
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(clientInput, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sendButton, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void connectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_connectButtonActionPerformed
        // TODO add your handling code here:
        startClient();
        connectButton.setEnabled(false);//to prevent if the user wants to press multi times 
        username.setEditable(false);
        Thread client = new Thread(new ClientThread());     
        client.start();

    }//GEN-LAST:event_connectButtonActionPerformed

    private void disconnectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_disconnectButtonActionPerformed
        // TODO add your handling code here:
        isConnected = false;
        clientDisconnect();
        sendButton.setEnabled(false);//disable clients to send message after disconnect
        disconnectButton.setEnabled(false);
        connectButton.setEnabled(true);        
    }//GEN-LAST:event_disconnectButtonActionPerformed

    private void sendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendButtonActionPerformed
        // TODO add your handling code here:
        FileOutputStream fs = null;
        String input = clientInput.getText();
        String client = username.getText();
        String[] temp;
        String filename = "";
        File aFile;
        boolean findFile = false;
        clientChat.append(client + ":" + input+"\n");
        clientInput.setText(" ");
        //boolean found=false;
        try {
            pw = new PrintWriter(socket.getOutputStream(), true);
            //pw.println(client+" "+);
            pw.println(client + ":" + input + "\n");
            /*
             Only will work when the current client creates the file,
             add the file name, the execute the writing into the file.
             if the server closed but the file is still in the folder, 
             when runs server again, the client still can't write into the file
             */

            if (input.contains("File")) {
                temp = input.split(" ");
                filename = temp[1];
                aFile = new File("src\\ada20\\" + filename + ".txt");
                if (aFile.createNewFile()) {//Command File filename
                    fs = new FileOutputStream(aFile);
                    fileStream.add(fs);
                    pw.println("\n" + client + " created a file called " + aFile.getName());
                    file.add(aFile.getName());
                    fileAdded = true;
                }
            } else if (input.contains("Write") && fileAdded) {//find if the file is in the directory command is:Write filename text
                // System.out.println(file.size());
                String[] writeFile;
                writeFile = input.split(" ");
                String searchFile = writeFile[1];
                // boolean find=false;
                System.out.println(writeFile.length);

                for (String s : file) {
                    if (s.equalsIgnoreCase(searchFile+".txt")) {
                        pw.println("File found\n");
                        int textLength = writeFile.length;
                        FileWriter writer = new FileWriter("src\\ada20\\"+searchFile+".txt");
                        String[] t = input.split(":");                        
                        writer.write(t[1]);
                        writer.close();
                    } else {
                        System.out.println("Can't find file");
                    }
                }

            } else if (input.contains("Read") && fileAdded) {
                /*When a client wants to read the file*/
                String[] readFile;
                readFile = input.split(" ");
                String searchFile = readFile[1];
                // boolean find=false;
                System.out.println(readFile.length);

                for (String s : file) {
                    if (s.equalsIgnoreCase(searchFile + ".txt")) {
                        // System.out.println("File found!");
                        pw.println("File found\n");
                        int textLength = readFile.length;
                        FileReader reader = new FileReader("src\\ada20\\" + searchFile + ".txt");
                        br = new BufferedReader(reader);//used for read the lines inside the selected file(s)
                        String line;
                        while ((line = br.readLine()) != null) {
                            pw.println(client + "'s " + searchFile + ".txt" + ":" + line + "\n");
                            clientChat.append("\n"+client + "'s " + searchFile + ".txt" + ":" + line);
                        }
                    } else {
                        pw.println("Can't find file");
                    }
                }
            } else if (input.contains("Chat")) {
                pw.println(client + " starts a private chat\n");
                /*when a client wants to have a private chat,
                  needs to give the client name to the server and server helps clients to process the private chat
                */
            }             
            clientChat.setCaretPosition(clientChat.getDocument().getLength());
           pw.flush();
        } catch (Exception e) {
            clientChat.append("\nError in printwriter " + e);
        }    
    }//GEN-LAST:event_sendButtonActionPerformed

    public void clientDisconnect() {
        /*When the user wants to disconnect with the server*/
        String disconnectUser = username.getText();
        clientChat.append(username.getText() + " is disconnected\n");
        try {
            pw = new PrintWriter(socket.getOutputStream(), true);
            pw.println(disconnectUser + " is disconnected\n");
            pw.flush();
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Client().setVisible(true);
            }
        });
    }

    public class ClientThread implements Runnable {
/*Runs the client thread*/
        @Override
        public void run() {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            String input = "";
            String[] data;
            String clientName = username.getText();
            onlineUser.setFont(new Font("Serif", Font.ITALIC, 17));//set the text size in the text area
            /*The online user is to list the online users in the online user text area*/
            try {
                br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                pw = new PrintWriter(socket.getOutputStream(), true);
                /*The code below is to handle the disconnect or connect the server*/
                while ((input = br.readLine()) != null) {
                    clientChat.append("\n" + input);//print "A greeting from the server to client chat side
                    if (input.contains("Online")) {
                        data = input.split(" ");
                        //otherUsers.add(data[2]);
                        onlineUser.append(data[2] + "\n");
                    } else if (input.contains("disconnected")) {
                        data = input.split(" ");    
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea clientChat;
    private javax.swing.JTextField clientInput;
    private javax.swing.JButton connectButton;
    private javax.swing.JButton disconnectButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea onlineUser;
    private javax.swing.JButton sendButton;
    private javax.swing.JTextField username;
    // End of variables declaration//GEN-END:variables
}