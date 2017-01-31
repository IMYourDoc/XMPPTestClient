package mucsimulationtool;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


//import utils.*;

import org.jivesoftware.smack.packet.Message;


public class CustomStanzaCreator extends Message {
    private String customStanza;

    public void setCustomStanza(String customStanza) {
        this.customStanza = customStanza;
    }

    
    //main 
//    public String toXML() {
//        String XMLMessage = super.toXML();
//        String XMLMessage1 = XMLMessage.substring(0, XMLMessage.indexOf(">"));
//        String XMLMessage2 = XMLMessage.substring(XMLMessage.indexOf(">"));
//        if (this.customStanza != null) {
//            XMLMessage1 = XMLMessage1 + this.customStanza;
//        }
//        return XMLMessage1 + XMLMessage2;
//    }
    
    
     //main 
    public String toXML() {
        String XMLMessage = super.toXML();
        String XMLMessage1 = XMLMessage.substring(0, XMLMessage.indexOf(">"));
        String XMLMessage2 = XMLMessage.substring(XMLMessage.indexOf(">"));
        
        
        
        
        if (this.customStanza != null) {
            XMLMessage1 = XMLMessage1 + this.customStanza;
        }
        return XMLMessage1 + XMLMessage2;
    }
    
  
    
    /*
<message id="0co0T-0"my data here></message>
    
    
<message to="dfpatient2_1482876181861@newconversation.imyourdoc.com" 
id="005F8289-199E-43DB-A62C-769BCE341886" 
type="groupchat" 
from="vanceweb2@imyourdoc.com/Webchat">
<body>
{"messageID":"005F8289-199E-43DB-A62C-769BCE341886",
"timestamp":"2016-12-27 22:05:53.402 +0000",
"content":"I just swapped the code to add them instead.",
"from":"vanceweb2@imyourdoc.com",
"to":"dfpatient2_1482876181861@newconversation.imyourdoc.com"}
</body>
<properties 
xmlns="http://www.jivesoftware.com/xmlns/xmpp/properties">
<property>
<name>message_version</name>
<value type="string">2.0</value>
</property>
</properties>
</message>

    */
        
    
    
    
    
    
    
}