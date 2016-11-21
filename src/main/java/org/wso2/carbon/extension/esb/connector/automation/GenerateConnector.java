package org.wso2.carbon.extension.esb.connector.automation;
/*
* Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

import org.wso2.carbon.extension.esb.connector.automation.util.AutomationConstants;
import org.wso2.carbon.extension.esb.connector.automation.wsdl.GenerateWSDLConnector;

/**
 * Web Services sample client. Uses the SAAJ library to consume a web service.
 */

public class GenerateConnector {

    public static void main(String[] args) {
        System.out.println(AutomationConstants.WELCOME_MESSAGE);

        GenerateC4Connector generateC4Connector;
        generateC4Connector = new GenerateWSDLConnector();
        generateC4Connector.readResource();
    }
}