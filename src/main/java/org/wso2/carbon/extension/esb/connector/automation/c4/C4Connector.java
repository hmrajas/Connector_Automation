package org.wso2.carbon.extension.esb.connector.automation.c4;/*
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

import org.apache.commons.io.FileUtils;
import org.wso2.carbon.extension.esb.connector.automation.GeneratePomXML;
import org.wso2.carbon.extension.esb.connector.automation.init.GenerateSFInit;
import org.wso2.carbon.extension.esb.connector.automation.init.Init;
import org.wso2.carbon.extension.esb.connector.automation.util.AutomationConstants;
import org.wso2.carbon.extension.esb.connector.automation.util.OperationInfo;
import org.wso2.carbon.extension.esb.connector.automation.wsdl.ServiceInfo;

import java.io.File;
import java.util.Iterator;
import java.util.List;

public class C4Connector {
    List components;
    Init init;
    Boolean isInitExist = false;
    String authenticationMethod = null;
    String mimeType = AutomationConstants.TEXT_XML;

    /**
     *
     * @param components
     * @param authenticationMethod
     */
    public C4Connector(List components, String authenticationMethod) {
        this.components = components;
        this.authenticationMethod = authenticationMethod;

    }

    /**
     *
     */
    public void generateC4Connector() {
        GenerateC4Connector generateC4Connector = new GenerateC4Connector();
        try {
            File srcDir = new File(String.valueOf(AutomationConstants.ARCHETYPE_RESOURCE_PATH));
            String connectorName = components.get(0).toString();
            String destination = "./" + connectorName;
            File destDir = new File(destination);
            FileUtils.copyDirectory(srcDir, destDir);
            String connectorXMLPath = destination + AutomationConstants.RESOURCE_PATH;
            generateC4Connector.generateConnectorXML(connectorName, connectorXMLPath, components);
            Iterator component = components.iterator();
            GeneratePomXML.generatePomXml(connectorName, destination);
            while (component.hasNext()) {
                ServiceInfo serviceInfo = (ServiceInfo) component.next();
                Iterator operations = serviceInfo.getOperations();
                String componentPath = connectorXMLPath + connectorName;
                new File(connectorName).mkdir();
                File compDir = new File(componentPath);
                FileUtils.forceMkdir(compDir);
                if (authenticationMethod.equals(AutomationConstants.SALESFORCE)) {
                    isInitExist = true;
                    init = new GenerateSFInit();
                    init.generateInitXML(componentPath, mimeType);
                }
                generateC4Connector.generateComponentXML(serviceInfo.operations, componentPath, connectorName, isInitExist);
                while (operations.hasNext()) {
                    OperationInfo operationInfo = (OperationInfo) operations.next();
                    generateC4Connector.generateMethodXML(operationInfo, componentPath, mimeType);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
