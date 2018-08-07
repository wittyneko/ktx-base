//def jitpackRepository = "com.github"
//def jitpackRepository = "com.gitlab"
//def jitpackRepository = "com.gitee"
//def jitpackRepository = "org.bitbucket"
def jitpackRepository = "com"
jitpackRepository = localProperties.getProperty("jitpackRepository", jitpackRepository)

ext {
    implementation_list = [
            dependencies_map['appcompat-v7'],
            dependencies_map['kotlin-stdlib-jdk7'],
            dependencies_map['anko'],

            dependencies_map['retrofit'],
            dependencies_map['glide'],
            dependencies_map['lifecycle-extensions'],
            dependencies_map['lifecycle-runtime'],
            dependencies_map['lifecycle-common-java8'],
    ]

    // 个人库
    implementation_list += [
//            "${jitpackRepository}.wittyneko:ktx-base:1.0.0-SNAPSHOT",
    ]

    apt_list = [
            dependencies_apt["lifecycle-compiler"]
    ]
}