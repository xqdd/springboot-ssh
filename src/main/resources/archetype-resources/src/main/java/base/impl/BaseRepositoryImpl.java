package ${groupId}.base.impl;

import ${groupId}.base.BaseRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.io.Serializable;

public class BaseRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements BaseRepository<T, ID> {
    private final EntityManager entityManager;
    private final JpaEntityInformation<T, ID> entityInformation;

    public BaseRepositoryImpl(JpaEntityInformation<T, ID> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
        this.entityInformation = entityInformation;
    }



    @Transactional
    public void resetId() {
        if(entityInformation.getIdType()== Integer.class){
            this.entityManager.createNativeQuery("alter table `" + this.camel2Underline(this.entityInformation.getEntityName()) + "` auto_increment=1").executeUpdate();
        }
    }


    @Transactional
    public void executeSql(String... sqls) {
        for (String sql : sqls) {
            if (!StringUtils.isBlank(sql)) {
                entityManager.createNativeQuery(sql).executeUpdate();
            }
        }
    }


    /**
     * 驼峰命名法转下划线
     *
     * @param source 源
     * @return 返回值
     */
    private String camel2Underline(String source) {
        if (source != null && !source.trim().isEmpty()) {
            source = source.trim();
            StringBuilder sb = new StringBuilder();
            sb.append(String.valueOf(source.charAt(0)).toLowerCase());

            for (int i = 1; i < source.length(); ++i) {
                String s = String.valueOf(source.charAt(i));
                if (s.toUpperCase().equals(s) && !Character.isDigit(s.charAt(0))) {
                    sb.append("_");
                }

                sb.append(s.toLowerCase());
            }

            return sb.toString();
        } else {
            return source;
        }
    }
}
