package ${groupId}.utils;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtobufIOUtil;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import org.jboss.logging.Logger;

/**
 * 序列化工具
 */
class ProtoBufUtil {
    private static Logger log = Logger.getLogger(${groupId}.utils.ProtoBufUtil.class);
    private static LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);


    public static <T> byte[] serializer(T o) {
        try {
            Schema<T> schema = RuntimeSchema.getSchema((Class<T>) o.getClass());
            return ProtobufIOUtil.toByteArray(o, schema, buffer);
        } catch (Exception e) {
            log.error("Protobuf 序列化失败", e);
            return null;
        } finally {
            buffer.clear();
        }
    }


    public static <T> T deserializer(byte[] bytes, Class<T> clazz) {
        try {
            Schema<T> schema = RuntimeSchema.getSchema(clazz);
            T objParsed = schema.newMessage();
            ProtostuffIOUtil.mergeFrom(bytes, objParsed, schema);
            return objParsed;
        } catch (Exception e) {
            log.error("Protobuf 反序列化失败", e);
            return null;
        }
    }


}