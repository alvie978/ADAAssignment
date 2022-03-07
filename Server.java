
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ada20;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.awt.image.ImageProducer;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alvie Zhang
 */
public class Server extends javax.swing.JFrame {
    public String clientInput;
    public boolean stopRequest;
    public static final int PORT = 2333; // some unused port number
    public static boolean serverStop;
    public boolean clientConnected = false;
    public boolean removed = false;
    ServerSocket serverSocket = null;
    PrintWriter pw;
    Socket so;
    Chat connectedClients;
    ArrayList<String> onlineUsers = new ArrayList<String>();
    ArrayList userInput;
    ArrayList<String> specificChat = new ArrayList<String>();    
    ArrayList pChat = new ArrayList();//store private chat's arraylist
    ArrayList clientInfo = new ArrayList();

    /**
     * Creates new form Server
     */
    public Server() {
        initComponents();
    }
    /**
     *@method : tellEveryClient
     * @param String name
     * This function is to give the server announcement to all the clients
     * The userInput arraylist is to store all the clients' PrintWriter
     */
    public void tellEveryClient(String message) {
        Iterator it = userInput.iterator();
        while (it.hasNext()) {
            try {
                PrintWriter serverAnnounce = (PrintWriter) it.next();
                serverAnnounce.println(message);
                serverAnnounce.flush();
                server_announce.setCaretPosition(server_announce.getDocument().getLength());
            } catch (Exception e) {
                server_announce.append("Server sending message fail: " + e);
            }

        }
    }
/**
 *@method findUser
 *@param username
 * This method is to find the users the client wants to have a private chat with
 * After find the user,take the prinwriter of those user to a new arraylist
 * Iterate the new arraylist to achieve private chat
 */
    public void findUser(String user1) {
        //  System.out.println(clientInfo.size());
        String pc = "";
        String[] pcWriter;
        for (int i = 0; i < clientInfo.size(); i++) {
            if (clientInfo.get(i).toString().contains(user1)) {
                pc = clientInfo.get(i).toString();
                pcWriter = pc.split(",");//To get the printwriter object from the Chat class's arraylist(clientInfo)
               //Because I store like username, printwriter, so split ", "to get the printwriter object
                for (int h = 0; h < userInput.size(); h++) {
                    if (userInput.get(h).toString().equalsIgnoreCase(pcWriter[1])) {
                        pChat.add(userInput.get(h));
                        /**
                         Because the userInput arrayList store prinwriter and the clientInfo store the
                         * client information at the same time, check if the printwriters are match, if same,
                         * take the same printwriter from the userInput arrayList to the pChat arraylist
                         * I didn't take the printwriter object from the clientInfo arraylist was becase the 
                         * printwriter object in the clientInfo I stored as toString, can't convert string object to 
                         * printwriter object
                         */
                    }

                }
            }
        }

    }
/*PrivateChat method works similar way with tellEveryClient method*/
    public void privateChat(String message) {
        // server_announce.append(message+" here");
        Iterator it = pChat.iterator();
        while (it.hasNext()) {
            try {
                PrintWriter pc = (PrintWriter) it.next();
                pc.println(message);
                pc.flush();
                server_announce.setCaretPosition(server_announce.getDocument().getLength());
            } catch (Exception e) {
                server_announce.append("Private sending message fail: " + e);
            }

        }
        pChat.clear();
        /*Clear the data in the pChat after client output the command Chat user : message
        so that the other client can also use the private chat
        */
    }

    public class ClientManager implements Runnable {
/*To manage each client*/
        BufferedReader bReader;
        Socket socket;
        PrintWriter client;
        PrintWriter ou;

        public ClientManager(Socket socket, PrintWriter client) {
            this.client = client;
            this.socket = socket;
            InputStreamReader reader;
            try {
                reader = new InputStreamReader(socket.getInputStream());
                bReader = new BufferedReader(reader);
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        @Override
        public void run() {
            /**
             * This run method is to detect the user input
            *
             */
            String chat = "";//a single message from client
            String[] clientChat;
            String name;

            try {
                client = new PrintWriter(socket.getOutputStream(), true);
                client.println("A greeting from server");//when a new user loged in, greeting to the new user
                
                while ((chat = bReader.readLine()) != null) {
                 //Starts to detect what's client's saying
                    if (chat.contains("disconnected")) {
                        //When a user leaves, a message from user side will output a message,if contains disconnect,tell server,
                        //server will do something
                        server_announce.append(chat);
                        tellEveryClient("\nServer:" + chat);
                        clientChat = chat.split(" ");
                        onlineUsers.remove(clientChat[0]);//remove the user from the list
                        for (int i = 0; i < clientInfo.size(); i++) {
                            /*
                            This loop is when the user wants to select a specific client to chat,
                            use two arraylists to store infomation, the online user is to store all the online user
                            the client info is to store client's information who wants to do a private chat                          
                            */
                            if (clientInfo.get(i).toString().contains(clientChat[0])) {
                                clientInfo.remove(i);                                
                            }
                        }
                        removed = true;
                    } else if (chat.contains("Chat"))//user and another user chat
                    {//command Chat user : message
                        clientChat = chat.split(" ");
                        findUser(clientChat[0]);
                        findUser(clientChat[1]);
                       server_announce.append(clientChat[0]+" is chat with "+clientChat[1]);
                        String[] t = chat.split(":");
                        privateChat("\nSecrete Chat: "+clientChat[0] + ":" + t[2]);
                     
                    } else if (chat.contains("File")) {
                       //when a client wants to do something with the file
                        server_announce.append(chat+"\n");
                        tellEveryClient("Server "+chat);
                    } else if (!chat.contains("Chat")) {
                        //Chat command is only for private chat
                        server_announce.append(chat+"\n");
                        tellEveryClient(chat);
                    }
                }                               
                server_announce.setCaretPosition(server_announce.getDocument().getLength());
                client.flush();
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void startServer() {
        stopRequest = false;
        server_announce.setFont(new Font("Serif", Font.ITALIC, 23));

        try {
            serverSocket = new ServerSocket(PORT);
            server_announce.append("Server started at: " + InetAddress.getLocalHost() + " on port: " + PORT);
            /*User menu*/
            server_announce.append("\nHere is the user menu (commands)\n");
            server_announce.append("File filename (Create a new file)\n");
            server_announce.append("Write filename : text (write in exist a file)\n");
            server_announce.append("Read filename (Output the text in the file)\n");
            server_announce.append("Chat username : messages  (private chat with someone)\n");
            server_announce.append("Please be careful of the whitespaces.\nEach right and left of ':'one whitespace!Otherwise it won't work!");
            server_announce.append("\nThe commands are case sensitive");
            server_announce.append("\nWhite space before your first letter will also cause error\n");

        } catch (IOException e) {
            server_announce.append("Server can't listen on port: " + e);
            System.err.println("Server can't listen on port: " + e);
            System.exit(-1);
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        server_announce = new javax.swing.JTextArea();
        startButton = new javax.swing.JButton();
        endButton = new javax.swing.JButton();
        oUsersButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        server_announce.setColumns(20);
        server_announce.setRows(5);
        jScrollPane1.setViewportView(server_announce);

        startButton.setText("Start");
        startButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startButtonActionPerformed(evt);
            }
        });

        endButton.setText("End");
        endButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                endButtonActionPerformed(evt);
            }
        });

        oUsersButton.setText("Online Users");
        oUsersButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                oUsersButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(startButton, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(138, 138, 138)
                .addComponent(endButton, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(oUsersButton, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 673, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 377, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(startButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(endButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(oUsersButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startButtonActionPerformed
        // TODO add your handling code here:
        startServer();
        stopRequest = false;
        Thread begin = new Thread(new ServerSide());
        begin.start();
        startButton.setEnabled(false);
        /*Disable startButton to make sure the user won't press it more that one time*/
    }//GEN-LAST:event_startButtonActionPerformed

    private void endButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_endButtonActionPerformed
        try {
            // TODO add your handling code here:
            stopRequest = true;
            server_announce.append("\nServer stoped, bye");
            Thread.sleep(5000);
            onlineUsers.clear();
            tellEveryClient("The server is closed, thanks for joining us\n");
            server_announce.setText("");
            serverStop = true;

        } catch (InterruptedException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_endButtonActionPerformed

    private void oUsersButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_oUsersButtonActionPerformed
        // TODO add your handling code here:
        server_announce.append("Online users: \n");
        for (String clients : onlineUsers) {
            server_announce.append(clients + "\n");
            tellEveryClient("\nOnline user " + clients + ".");//update the online user list
        }
    }//GEN-LAST:event_oUsersButtonActionPerformed

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
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Server().setVisible(true);
            }
        });
    }

    public class ServerSide implements Runnable {
        private Socket s;
        String clientUsername = "";
        public String toString() {
            return clientUsername;//used to printout all the online users
        }

        @Override
        public void run() {
            PrintWriter pWriter;
            BufferedReader br;
            userInput = new ArrayList();
            PrintWriter pcw;
            try {
                while (!stopRequest) {
                    Socket users = serverSocket.accept();
                    clientConnected = true;
                    pWriter = new PrintWriter(users.getOutputStream());
                    userInput.add(pWriter); 
                    Thread listener = new Thread(new ClientManager(users, pWriter));
                    listener.start();

                    br = new BufferedReader(new InputStreamReader(users.getInputStream()));
                    clientUsername = br.readLine();
                    connectedClients = new Chat(clientUsername, pWriter);//for private chat purpose
                    clientInfo.add(connectedClients);
                    server_announce.append("\nClient " + clientUsername + " connected\n");
                    tellEveryClient("Server:Client " + clientUsername + " joined! Yeah!\n");
                    onlineUsers.add(clientUsername);
                }
                serverSocket.close();
            } catch (IOException e) {
                System.err.println("Can't accept client connection: " + e);
            }
            System.out.println("Server finishing");
        }
    }
    /*
     This class is only for store clients' information like printwriter, username
     This class is to store a single client's information
     */

    public class Chat {

        private String clientName;
        private PrintWriter printWriter;

        public Chat(String clientName, PrintWriter printWriter) {
            this.setClientName(clientName);
            this.setPrintWriter(printWriter);

        }

        public String getClientName() {
            return clientName;
        }

        public void setClientName(String clientName) {
            this.clientName = clientName;
        }

        public PrintWriter getPrintWriter() {
            return printWriter;
        }

        public void setPrintWriter(PrintWriter printWriter) {
            this.printWriter = printWriter;
        }

        public String toString() {
            return this.clientName + "," + this.printWriter;
        }

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton endButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton oUsersButton;
    private javax.swing.JTextArea server_announce;
    private javax.swing.JButton startButton;
    // End of variables declaration//GEN-END:variables
}
