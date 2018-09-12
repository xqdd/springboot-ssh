import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Tools {

    private String pathPrefix = new File("").getAbsolutePath()
            + "/src/main/java/${groupId}/mvc/";
    private String entityPath = pathPrefix + "bean/entity/";
    private String repositoryPath = pathPrefix + "repository/";
    private String servicePath = pathPrefix + "service/";

    private String templatePath = new File("").getAbsolutePath()
            + "/src/test/java/template/";
    private String repositoryTemplate = new BufferedReader(new FileReader(new File(templatePath + "repository.template"))).lines().collect(Collectors.joining("\n"));
    private String serviceTemplate = new BufferedReader(new FileReader(new File(templatePath + "service.template"))).lines().collect(Collectors.joining("\n"));

    public Tools() throws FileNotFoundException {
    }

    /**
     * 根据entity自动生成service和repository
     */
    @Test
    public void generateRepositoryAndService() {
        generateRepositoryAndService(entityPath, new ArrayList<>());

    }


    private void generateRepositoryAndService(String path, List<String> relatePaths) {
        var file = new File(path);
        var files = file.listFiles();
        if (files != null) {
            Stream.of(files).parallel().forEach(f -> {
                String fileName = f.getName();
                if (f.isDirectory()) {
                    if (fileName.equals("other")) return;
                    List<String> tempRelatePaths = new ArrayList<>(relatePaths);
                    tempRelatePaths.add(fileName);
                    generateRepositoryAndService(path + fileName + File.separator, tempRelatePaths);
                } else {
                    try {
                        //获取名称
                        String fileNameWithoutSuffix = fileName.substring(0, fileName.lastIndexOf("."));
                        File repositoryFile = new File(repositoryPath + fileNameWithoutSuffix + "Repository.java");
                        File serviceFile = new File(servicePath + fileNameWithoutSuffix + "Service.java");
                        if (repositoryFile.exists() && serviceFile.exists()) {
                            return;
                        }
                        //获取id类型
                        String idType = new BufferedReader(new FileReader(f))
                                .lines()
                                .filter(line -> line.contains("id;"))
                                .findFirst()
                                .map(line -> line.trim().split("\\s+")[1])
                                .orElse("Integer");
                        //获取小写名称
                        String lowerName = Character.toLowerCase(fileNameWithoutSuffix.charAt(0)) + fileNameWithoutSuffix.substring(1);
                        //复制repository
                        String relatePath = String.join(".", relatePaths) + ".";
                        if (!repositoryFile.exists()) {
                            try (var writer = new BufferedWriter(new FileWriter(repositoryFile))) {
                                writer.write(repositoryTemplate
                                        .replaceAll("__ID_TYPE__", idType)
                                        .replaceAll("__NAME__", fileNameWithoutSuffix)
                                        .replaceAll("__RELATE_PATH__", relatePath)
                                );
                            }
                        }
                        //复制service
                        if (!serviceFile.exists()) {
                            try (var writer = new BufferedWriter(new FileWriter(serviceFile))) {
                                writer.write(serviceTemplate
                                        .replaceAll("__ID_TYPE__", idType)
                                        .replaceAll("__NAME__", fileNameWithoutSuffix)
                                        .replaceAll("__RELATE_PATH__", relatePath)
                                        .replaceAll("__LOWER_NAME__", lowerName)
                                );
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

}
