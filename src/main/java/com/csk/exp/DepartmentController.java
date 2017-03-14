package com.csk.exp;

import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.csk.util.FormCheck;
import com.csk.util.MailUtils;

import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.UnsupportedEncodingException;
import java.security.Security;
import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


@Controller
public class DepartmentController {
    static boolean flag = false;
    static String regex = "";
    @Autowired
    private OwnerUnitRepository ownerUnitRepository;
    @GetMapping(value = "/addOwnerUnit")
    public String addOwnerUnit(@RequestParam(name = "ownerName") String ownerName,
            @RequestParam(name = "email") String email) throws AddressException, MessagingException {
        OwnerUnit ownerUnit = new OwnerUnit();
        Date current = new Date();
        long currentTimeStamp = current.getTime();
        List<OwnerUnit> findUnit = ownerUnitRepository.findByEmail(email);
        if (!FormCheck.isEmailValid(email)) {
            return "fail";
        }
        if (findUnit.isEmpty()) {
            ownerUnit.setOwnerName(ownerName);
            ownerUnit.setEmail(email);
            ownerUnit.setTimeStamp(currentTimeStamp);
            ownerUnitRepository.save(ownerUnit);
        } else {
            OwnerUnit temp = findUnit.get(0);
            if ((currentTimeStamp - temp.getTimeStamp()) < 15000)
                return "finish";
            else {
                temp.setTimeStamp(currentTimeStamp);
                ownerUnitRepository.save(temp);
            }
        }
        String subject = "项目概况表登记下载";
        String content = "您好," + ownerName + "：" + "\n项目概况表及样表请点击以下链接下载："
                + "\n http://120.25.157.166/regist/profiles.rar" + "\n\n  各业主单位将填写完整的资料（3个工作日内）发至以下公司邮箱："
                + "\n\n  \t广东建筑消防设施检测中心有限公司" + "\n  \t邮箱：thxfxmbgs@163.com   徐小伟：13512774017"
                + "\n\n  \t清大安质消防安全管理质量评价（北京）中心" + "\n  \t邮箱：qdazthxmb@sina.com   张文海：18604608236"
                + "\n\n  \t广东华建电气消防安全检测有限公司" + "\n  \t邮箱：247630634@qq.com   黄志健：18925069535" + "\n\n"
                + "\n\n--------------" + "\n\n天河区消防安全委员会";
        String attachmentPath = "";
        // SendMail(subject,content,attachmentPath,email);
        return "finish";
    }
    
    /**
     * <br>
     * 方法说明：主方法，用于测试 <br>
     * 输入参数： <br>
     * 返回类型：
     */
/*    @RequestMapping("/SendToAll")
    public void SendToAll(){
            MailUtils sendmail = new MailUtils();
            sendmail.setHost("smtp.qiye.163.com");
            sendmail.setUserName("service@accfutures.com");
            sendmail.setPassWord("XXXXXX");
            sendmail.setTo("EEEEEEE@accfutures.com,EEEEEE@qq.com,EEEEEEE@163.com");
            String nick = "";
            try {
                nick = javax.mail.internet.MimeUtility.encodeText("天河区消防安全委员会");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            sendmail.setFrom(nick + " <" + username + "@sina.com>");
            sendmail.setSubject("项目概况表登记下载");
            sendmail.setContent("您好," + ownerName + "：" + "\n项目概况表及样表请查看附件。"
                    + "\n\n  各业主单位将填写完整的资料（3个工作日内）发至相应公司邮箱："
                    + "\n\n  \t广东建筑消防设施检测中心有限公司" + "\n  \t邮箱：thxfxmbgs@163.com   徐小伟：13512774017"
                    + "\n\n  \t清大安质消防安全管理质量评价（北京）中心" + "\n  \t邮箱：qdazthxmb@sina.com   张文海：13804508305"
                    + "\n\n  \t广东华建电气消防安全检测有限公司" + "\n  \t邮箱：247630634@qq.com   黄志健：18925069535" + "\n\n"
                    + "\n\n--------------" + "\n\n天河区消防安全委员会");
            sendmail.attachfile("d:\\news.rar");
            sendmail.attachfile("d:\\jhjl.rar");
            
            System.out.println(sendmail.sendMail());

    }*/
    
    @GetMapping(value = "getOwnerUnitList")
    public List<OwnerUnit> getOwnerUnitList() {

        return ownerUnitRepository.findAll();
    }

    @GetMapping(value = "getOwnerUnitById")
    public OwnerUnit getOwnerUnitById(@RequestParam("id") Integer id) {

        return ownerUnitRepository.findOne(id);
    }

    @GetMapping(value = "getOwnerUnitByOwnerName")
    public List<OwnerUnit> getOwnerUnitByOwnerName(@RequestParam("ownerName") String ownerName) {
        return ownerUnitRepository.findByOwnerName(ownerName);
    }

    @GetMapping(value = "getOwnerUnitByEmail")
    public List<OwnerUnit> getOwnerUnitByEmail(@RequestParam("email") String email) {
        return ownerUnitRepository.findByEmail(email);
    }


     @PutMapping(value="updateOwnerUnitById") public OwnerUnit
     updateOwnerUnitById(@RequestParam("id") Integer id,
     
     @RequestParam("ownerName") String ownerName,
     
     @RequestParam("email") String email){ OwnerUnit ownerUnit =
     ownerUnitRepository.findOne(id); if(!email.equals("")){
     ownerUnit.setEmail(email); } if(!ownerName.equals("")){
     ownerUnit.setOwnerName(ownerName); } return
     ownerUnitRepository.save(ownerUnit); }

     @DeleteMapping(value = "deleteOwnerUnitById") public void
     deleteOwnerUnitById(@RequestParam("id") Integer id){
     ownerUnitRepository.delete(id); }

}
