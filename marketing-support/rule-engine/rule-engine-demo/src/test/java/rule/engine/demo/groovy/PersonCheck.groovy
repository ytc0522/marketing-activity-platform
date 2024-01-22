package rule.engine.demo.groovy

import rule.engine.groovy.dto.ExecuteParams

class PersonCheck extends Script {


    boolean sayHello(ExecuteParams params) {
        def age = params.get("age")
        if (age > 10) {
            return true
        }
        return false;
    }

    @Override
    Object run() {
        return null
    }
}


