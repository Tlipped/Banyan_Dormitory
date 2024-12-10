
## 目录和文件说明

### src/main/java/com/banyan_dormitory/

- **controller/**: 包含所有与 FXML 文件交互的控制器类。
- **model/**: 定义了应用中的数据模型类，如用户、管理员、访客等实体类。
- **service/**: 实现了业务逻辑和服务层的方法，例如对数据库的增删改查操作。
- **util/**: 提供了一系列工具类，如数据库连接管理、字符串验证等辅助功能。
- **Main.java**: 应用程序的主入口点。

### src/main/resources/

- **fxml/**: 存放所有的 FXML 文件，这些文件定义了 JavaFX 界面布局。
- **css/**: 样式表文件（目前尚未使用）。
- **images/**: 存储项目中使用的图像资源。
- **db/**: 包含数据库同步所需的 SQL 脚本以及 `database.properties` 配置文件。

### src/test/

测试代码的存放位置（当前项目中尚无测试代码）。

### pom.xml

Maven 的项目对象模型 (POM) 文件，定义了项目的依赖关系和其他构建配置。

### README.md

包含项目的介绍、安装指南、使用方法等信息。

### .gitignore

指定 Git 应该忽略的文件或目录，确保敏感信息不会被提交到版本控制系统中。

## 使用说明

1. **环境准备**:
   - 确保安装了 JDK（17） 和 Maven。
   - 使用 IDE（ IntelliJ IDEA）导入项目。
   - javaFx导入，在项目结构里的库，点击“+”,导入你本地的javafx/lib文件
   - 
2. **数据库配置**:
   - 修改 `src/main/resources/db/database.properties` 文件中的数据库连接信息。
   - 如果需要初始化数据库，请运行 `src/main/resources/db/migration` 目录下的 SQL 脚本。

3. **运行项目**:
   - 使用 Maven 命令行工具或 IDE 内置的 Maven 支持来编译和运行项目。
   - 可以通过运行 `Main.java` 来启动应用程序。

4. **开发建议**:
   - 在进行数据库结构变更时，请确保更新相应的 SQL 脚本，并通知团队成员。
   - 新增功能或修复问题后，记得更新相关文档。

5.**git提交拉取**：
  - 拉取远程仓库，打开git bash，cd到你想放的目录下，然后git clone 这个仓的ssh：/，然后idea打开
  - 提交是git add 你更改过的文件，然后git commit 备注信息给队友，git push到远程仓库（idea最左侧有个提交并推送，选好你的文件然后没什么问题就push上来）
  - 然后这里是可以管理版本的，也就是可以回退到之前版本的，所以比较方便

---

以上是项目的结构和使用说明。如果有任何疑问或需要进一步的帮助，请随时联系团队成员。
