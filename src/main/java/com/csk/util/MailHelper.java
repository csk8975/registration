package com.csk.util;

import java.io.UnsupportedEncodingException;
import java.security.Security;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MailHelper {
    public static String SendMail(String mailSubject, String mailContent, String attachmentPath, String mailTo)
            throws AddressException, MessagingException {
        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
        // Get a Properties object
        Properties props = System.getProperties();
        props.setProperty("mail.smtp.host", "smtp.sina.com");
        props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.auth", "true");
        final String username = "gzthxf";
        final String password = "gzthxf2017";
        Session session = Session.getDefaultInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        
        // -- Create a new message --
        Message msg = new MimeMessage(session);
        
        // -- Set the FROM and TO fields --
        String nick = "";
        try {
            nick = javax.mail.internet.MimeUtility.encodeText("天河区消防安全委员会");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        msg.setFrom(new InternetAddress(nick + " <" + username + "@ggkbigdata.com>"));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailTo, false));
        msg.setSubject(mailSubject);
        msg.setText(mailContent);
        msg.setSentDate(new Date());
        Transport.send(msg);

        System.out.println("Message sent to " + mailTo);

        return "success.";
    }
    
    public static String SendToOne(String mailTo, String ownerName)
            throws AddressException, MessagingException, UnsupportedEncodingException {
        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
        // Get a Properties object
        Properties props = System.getProperties();
        props.setProperty("mail.smtp.host", "smtp.sina.com");
        props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.auth", "true");
        final String username = "gzthxf";
        final String password = "gzthxf2017";
        Session session = Session.getDefaultInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        
        // -- Create a new message --
        MimeMessage msg = new MimeMessage(session);
        
        // -- Set the FROM and TO fields --
        String nick = "";
        try {
            nick = javax.mail.internet.MimeUtility.encodeText("天河区消防安全委员会");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        msg.setFrom(new InternetAddress(nick + " <" + username + "@sina.com>"));
        msg.setSubject("项目概况表登记下载");
        
        String content = "您好," + ownerName + "：" + "\n项目概况表及样表请查看附件。"
                + "\n\n  各业主单位将填写完整的资料（3个工作日内）发至相应公司邮箱："
                + "\n\n  \t广东建筑消防设施检测中心有限公司" + "\n  \t邮箱：thxfxmbgs@163.com   徐小伟：13512774017"
                + "\n\n  \t清大安质消防安全管理质量评价（北京）中心" + "\n  \t邮箱：qdazthxmb@sina.com   张文海：13804508305"
                + "\n\n  \t广东华建电气消防安全检测有限公司" + "\n  \t邮箱：247630634@qq.com   黄志健：18925069535" + "\n\n"
                + "\n\n--------------" + "\n\n天河区消防安全委员会";
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailTo, false));
     // 构造Multipart
        Multipart mp = new MimeMultipart();
        // 向Multipart添加正文
        MimeBodyPart mbpContent = new MimeBodyPart();
        mbpContent.setContent(content, "text/html;charset=gb2312");
        
        // 向MimeMessage添加（Multipart代表正文）
        mp.addBodyPart(mbpContent);

        // 向Multipart添加附件

        MimeBodyPart mbpFile = new MimeBodyPart();
        String filepath = ("");
        FileDataSource fds = new FileDataSource(filepath);
        mbpFile.setDataHandler(new DataHandler(fds));
        //这个方法可以解决附件乱码问题
        String filename= new String(fds.getName().getBytes(),"ISO-8859-1");

        mbpFile.setFileName(filename);
        // 向MimeMessage添加（Multipart代表附件）
        mp.addBodyPart(mbpFile);

        // 向Multipart添加MimeMessage
        msg.setContent(mp);
        msg.setSentDate(new Date());
        msg.saveChanges();

        //msg.setSentDate(new Date());
        Transport.send(msg);

        System.out.println("Message sent to " + mailTo);

        return "success.";
    }
    
    
}
