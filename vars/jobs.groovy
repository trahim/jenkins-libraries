def getJobs(String environment) {
    def pipelineFile = readProperties file: "pipelines.txt"
    pipelines = pipelineFile['pipelines'].tokenize(',')
    gitUrl = pipelineFile['gitUrl']
    for (int i=0; i<pipelines.size(); i++) {
        def name = pipelines[i]
        createJob(name, environment, gitUrl)
        build job: "${environment}/${name}"
    }
}

def createJob(String name, environment, gitUrl){
    step([
            $class: "ExecuteDslScripts",
            lookupStrategy: "SEED_JOB",
            scriptText: """
                folder("$environment")
                pipelineJob("${environment}/${name}") {
                    definition {
                        cpsScm {
                            scm {
                                git {
                                    remote {
                                        url("${gitUrl}")
                                        branch("master")
                                    }
                                }
                            }
                            scriptPath("${name}")
                        }
                    }
                }
            """
    ])
}