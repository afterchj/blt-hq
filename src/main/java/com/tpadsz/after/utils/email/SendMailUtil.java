package com.tpadsz.after.utils.email;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by hongjian.chen on 2019/3/30.
 * code 验证码
 * to 收件人
 * flag 验证码用途（reset用于忘记密码，bind用于绑定邮箱）
 */

public class SendMailUtil {

    public static void sendCode(String code, String to, String flag) {
        String subject = "";
        StringBuilder builder = new StringBuilder("亲爱的用户：您好！\n");
        if ("reset".equals(flag)) {
            subject = "【灯联网】找回您的账户密码";
            builder.append("    您收到这封这封电子邮件是因为您 (也可能是某人冒充您的名义) 申请了一个新的密码。假如这不是您本人所申请，请不用理会这封电子邮件，但是如果您持续收到这类的信件骚扰，请您尽快联络管理员。本次请求的校验码为：\n");
            builder.append("    " + code);
            builder.append("\n注意：请您在收到邮件 5 分钟内使用，否则该校验码将会失效。");
        } else if ("bind".equals(flag)) {
            subject = "【灯联网】邮箱绑定验证";
            builder.append("    您收到这封这封电子邮件是因为您 (也可能是某人冒充您的名义) 正在进行邮箱绑定验证。假如这不是您本人所申请，请不用理会这封电子邮件，但是如果您持续收到这类的信件骚扰，请您尽快联络管理员。本次请求的激活码为：\n");
            builder.append("    " + code);
            builder.append("\n注意：请您在收到邮件 5 分钟内使用，否则该激活码将会失效。");
        }
        String context = builder.toString();
        Properties properties = new Properties();
        properties.put("mail.transport.protocol", "smtp");// 连接协议
        properties.put("mail.smtp.host", "smtp.exmail.qq.com");// 主机名
//        properties.put("mail.smtp.port", 465);// 端口号
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.ssl.enable", "true");// 设置是否使用ssl安全连接 ---一般都使用
        properties.put("mail.debug", "true");// 设置是否显示debug信息 true 会在控制台显示相关信息
        // 得到回话对象
        Session session = Session.getInstance(properties);
        // 获取邮件对象
        Message message = new MimeMessage(session);
        Transport transport = null;
        try {
            // 设置发件人邮箱地址
            message.setFrom(new InternetAddress("info@tpadsz.com"));
            // 设置收件人邮箱地址
//        message.setRecipients(Message.RecipientType.TO, new InternetAddress[]{new InternetAddress("after@tpadsz.com"),new InternetAddress("766256898@qq.com")});
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));//一个收件人
            // 设置邮件标题
            message.setSubject(subject);
            // 设置邮件内容
            message.setText(context);
            // 得到邮差对象
            transport = session.getTransport();
            // 连接自己的邮箱账户
            transport.connect("info@tpadsz.com", "HH7eK9AjhcLGBXUT");// 密码为QQ邮箱开通的stmp服务后得到的客户端授权码
            // 发送邮件
            transport.sendMessage(message, message.getAllRecipients());
        } catch (MessagingException e) {
            e.printStackTrace();
        } finally {
            try {
                transport.close();
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        String code = "123456";
        String to = "766256898@qq.com";
        sendCode(code, to, "reset");
    }
}