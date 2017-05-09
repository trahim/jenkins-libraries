def call(body) {
    def config = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config
    body()

    node {
        stage 'terraform stuff should go here but I lost my free tier'
        echo "doing some stuff yo like printing things ${config.count}, ${config.size}"
    }
}