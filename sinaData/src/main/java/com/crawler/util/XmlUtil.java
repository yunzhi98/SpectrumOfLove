package com.crawler.util;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by wb-xuzhenbin on 2015/11/23.
 *
 */
public class XmlUtil implements Serializable {

    private static final long serialVersionUID = 4272705762921019405L;

    private static Logger log4j = Logger.getLogger(XmlUtil.class.getName());

    /**
     * ����xml�ļ�
     *
     * @param fileName String �����ļ���.
     * @param mapErrorMessage Map
     * @return Document XML�ĵ�
     */
    public static Document loadXML(String fileName,
                                   Map<String, Object> mapErrorMessage) {
        //�ж��ļ����Ƿ�Ϊnull
        if (fileName == null) {
            fileName = "";
        }
        // �ж��ļ��������Ƿ�Ϊ0
        if (fileName.length() == 0) {
        } else {
        }
        if (FileUtils.checkFile(fileName)) { //У���ļ�
            try {
                // ����xml������
                SAXReader sr = new SAXReader();
                //��xml�ļ�
                return sr.read(fileName);
            } catch (DocumentException ex) {
                mapErrorMessage.put("errorMessage", ex.getMessage());
                //��ʾ�ļ�������Ϣ
            }
        }
        return null;
    }

    public static Document loadXML(String fileName) {
        //�ж��ļ����Ƿ�Ϊnull
        if (fileName == null) {
            return null;
        }
        // �ж��ļ��������Ƿ�Ϊ0
        if (fileName.length() == 0) {
            return null;
        }
        if (FileUtils.checkFile(fileName)) { //У���ļ�
            try {
                File file=new File(fileName);
                System.out.println(file.exists());
                // ����xml������
                SAXReader sr = new SAXReader();
                //��xml�ļ�
                return sr.read(file);
            } catch (DocumentException ex) {
                //��ʾ�ļ�������Ϣ
                log4j.error("load config fail!" + ex.getMessage(),ex);
            }
        }
        return null;
    }

    /**
     * ����xml�ļ�
     *
     * @param document Document �ĵ�.
     * @param fileName String �����ļ���.
     */
    public static void saveXML(Document document, String fileName) {
        // �ж�Ŀ���ļ������Ƿ�Ϊnull
        if (fileName == null) {
            fileName = "";
        }
        // �ж�Ŀ���ļ����Ƴ����Ƿ�Ϊ0
        if (fileName.length() == 0) {
//			log4j.info("File name length is 0");
        } else {
            //����Ŀ���ļ�
            try {
                XMLWriter xmlWriter = new XMLWriter(new FileOutputStream(
                        fileName));
                xmlWriter.write(document);
                xmlWriter.close();
            } catch (Exception ex) {
                //����ӳ���ļ�������Ϣ
//				log4j.info(ex.getMessage());
            }
        }
    }

    /**
     * ����xml�ļ�
     *
     * @param document Document �ĵ�.
     * @param fileName String �����ļ���.
     * @param mapErrorMessage Map
     */
    public static void saveXML(Document document, String fileName,
                               Map<String, Object> mapErrorMessage) {
        // �ж�Ŀ���ļ������Ƿ�Ϊnull
        if (fileName == null) {
            fileName = "";
        }
        // �ж�Ŀ���ļ����Ƴ����Ƿ�Ϊ0
        if (fileName.length() == 0) {
            mapErrorMessage.put("errorMessage", "file name length is 0");
        } else {
            //����Ŀ���ļ�
            try {
                XMLWriter xmlWriter = new XMLWriter(new FileOutputStream(
                        fileName));
                xmlWriter.write(document);
                xmlWriter.close();
            } catch (Exception ex) {
                //����ӳ���ļ�������Ϣ
                mapErrorMessage.put("errorMessage", ex.getMessage());
            }
        }
    }

    /**
     * ȡ�ڵ������
     * @param element Element �ڵ�
     * @return String �ڵ�ֵ
     */
    public static String getElementName(Element element) {
        String elementName = ""; // ����ڵ����Ʊ���
        //�жϽڵ��Ƿ�Ϊnull
        if (element == null) {
        } else {
            //ȡ�ýڵ�����
            elementName = element.getName();
            //�жϽڵ������Ƿ�Ϊnull
            if (elementName == null) {
                elementName = "";
            }
        }
        return elementName;
    }

    /**
     * ȡ���ڵ�
     * @param document Document �ĵ�
     * @return Element ���ڵ�
     */
    public static Element getRootElement(Document document) {
        Element rootElement = null; //�����ӽڵ����

        // �жϽڵ��Ƿ�Ϊnull
        if (document == null) {
        } else {
            // ȡ���ӽڵ�
            rootElement = document.getRootElement();
        }
        return rootElement;
    }

    /**
     * ���ø��ڵ�
     * @param document Document �ĵ�
     * @param rootElementName String �ڵ�����
     * @return Element ���ڵ�
     */
    public static Element setRootElement(Document document,
                                         String rootElementName) {
        Element rootElement = null; //�����ӽڵ����

        // �жϽڵ��Ƿ�Ϊnull
        if (document == null) {
        } else {
            // ȡ���ӽڵ�
            rootElement = document.addElement(rootElementName);
        }
        return rootElement;
    }

    /**
     * ȡ�ڵ���ӽڵ�
     * @param element Element �ڵ�
     * @param childElementName String �ӽڵ�����
     * @return Element �ӽڵ�
     */
    public static Element getElementChildElement(Element element,
                                                 String childElementName) {
        Element childElement = null; //�����ӽڵ����

        // �ж��ӽڵ������Ƿ�Ϊnull
        if (childElementName == null) {
            childElementName = "";
        }
        // �жϽڵ��Ƿ�Ϊnull
        if (element == null) {
        } else {
            // �ж��ӽڵ����Ƴ����Ƿ�Ϊ0
            if (childElementName.length() == 0) {
            } else {
                // ȡ���ӽڵ�
                childElement = element.element(childElementName);
            }
        }
        return childElement;
    }

    /**
     * ���ýڵ���ӽڵ�
     * @param element Element �ڵ�
     * @param childElementName String �ӽڵ�����
     * @return Element �ӽڵ�
     */
    public static Element setElementChildElement(Element element,
                                                 String childElementName) {
        Element childElement = null; //�����ӽڵ����

        // �ж��ӽڵ������Ƿ�Ϊnull
        if (childElementName == null) {
            childElementName = "";
        }
        // �жϽڵ��Ƿ�Ϊnull
        if (element == null) {
        } else {
            // �ж��ӽڵ����Ƴ����Ƿ�Ϊ0
            if (childElementName.length() == 0) {
            } else {
                // ȡ���ӽڵ�
                childElement = element.addElement(childElementName);
                ;
            }
        }
        return childElement;
    }

    /**
     * ȡ�ڵ��ֵ
     * @param element Element �ڵ�
     * @return String �ڵ�ֵ
     */
    public static String getElementValue(Element element) {
        String elementValue = ""; // ����ڵ�ֵ����
        //�жϽڵ��Ƿ�Ϊnull
        if (element == null) {
        } else {
            //ȡ�ýڵ�ֵ
            elementValue = element.getTextTrim();
            //�жϽڵ�ֵ�Ƿ�Ϊnull
            if (elementValue == null) {
                elementValue = "";
            }
        }
        return elementValue;
    }

    /**
     * ���ýڵ��ֵ
     * @param element Element �ڵ�
     * @param elementValue String �ڵ�ֵ
     */
    public static void setElementValue(Element element, String elementValue) {
        // �жϽڵ��Ƿ�Ϊnull
        if (element == null) {
        } else {
            // �жϽڵ�ֵ�Ƿ�Ϊnull
            if (elementValue == null) {
                elementValue = "";
            }
            // �жϽڵ�ֵ�����Ƿ�Ϊ0
            if (elementValue.length() == 0) {
            } else {
                //���ýڵ�ֵ
                //element.addText("");
                element.setText(elementValue);

            }
        }
    }

    /**
     * ȡ�ڵ������
     * @param element Element �ڵ�
     * @param attributeName String ��������
     * @return String ����ֵ
     */
    public static String getElementAttribute(Element element,
                                             String attributeName) {
        String attributeValue = ""; //����ڵ�����ֵ����
        // �жϽڵ��Ƿ�Ϊnull
        if (element == null) {
        } else {
            //�жϽڵ����������Ƿ�Ϊnull
            if (attributeName == null) {
                attributeName = "";
            }
            //�жϽڵ��������Ƴ����Ƿ�Ϊ0
            if (attributeName.length() == 0) {
            } else {
                // ȡ�ýڵ�����ֵ
                attributeValue = element.attributeValue(attributeName);
                //�жϽڵ�����ֵ�Ƿ�Ϊnull
                if (attributeValue == null) {
                    attributeValue = "";
                }
            }
        }
        return attributeValue;
    }

    /**
     * ���ýڵ������
     * @param element Element �ڵ�
     * @param attributeName String ��������
     * @param attributeValue String ����ֵ
     */
    public static void setElementAttribute(Element element,
                                           String attributeName, String attributeValue) {
        // �жϽڵ��Ƿ�Ϊnull
        if (element == null) {
        } else {
            //�жϽڵ�����ֵ�Ƿ�Ϊnull
            if (attributeValue == null) {
                attributeValue = "";
            }
            //�жϽڵ����������Ƿ�Ϊnull
            if (attributeName == null) {
                attributeName = "";
            }
            //�жϽڵ��������Ƴ����Ƿ�Ϊ0
            if (attributeName.length() == 0) {
            } else {
                //���ýڵ�����ֵ
                element.addAttribute(attributeName, attributeValue);
            }
        }
    }

    /**
     * xpath�ڵ�
     * @param element Element �ڵ�
     * @param elementName String xpath�Ľڵ�����
     * @return List �ڵ��List��
     *
     */
    @SuppressWarnings("unchecked")
    public static List selectElementNodes(Element element, String elementName) {
        List elementList = null; //����Դ�ڵ��List������
        // �ж�xpath�Ľڵ������Ƿ�Ϊnull
        if (elementName == null) {
            elementName = "";
        }
        // �жϽڵ��Ƿ�Ϊnull
        if (element == null) {
        } else {
            // ��Ϊnull �ڽڵ���ȡList��
            elementList = element.selectNodes(elementName);
        }
        return elementList;
    }

    /**
     * xpath�ĵ�
     * @param document Document �ĵ�����
     * @param elementName String xpath�Ľڵ�����
     * @return List �ڵ��List��
     *
     */
    @SuppressWarnings("unchecked")
    public static List selectElementNodes(Document document, String elementName) {
        List elementList = null; //����Դ�ڵ��List������
        // �ж�xpath�Ľڵ������Ƿ�Ϊnull
        if (elementName == null) {
            elementName = "";
        }
        // �жϽڵ��Ƿ�Ϊnull
        if (document == null) {
        } else {
            // ��Ϊnull �ڽڵ���ȡList��
            //System.out.println(elementName);
            try {
                elementList = document.selectNodes(elementName);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        //System.out.println(elementList);
        return elementList;
    }

    /**
     * ���ýڵ�����
     *
     * @param element Element �ڵ�
     * @param elementName String ����
     */
    public static void setElementName(Element element, String elementName) {
        //�жϽڵ������Ƿ�Ϊnull
        if (elementName == null) {
            elementName = "";
        }
        //�жϽڵ��Ƿ�Ϊnull
        if (element == null) {
        } else {
            //ȡ�ýڵ�����
            element.setName(elementName);
        }
    }

    /**
     * ����ӽڵ�
     *
     * @param element Element �ڵ�
     * @param elementName String ����
     * @return Element
     */
    public static Element addChildElement(Element element, String elementName) {
        Element childElement = null;
        //�жϽڵ������Ƿ�Ϊnull
        if (elementName == null) {
            elementName = "";
        }
        //�жϽڵ��Ƿ�Ϊnull
        if (element == null) {
        } else {
            //ȡ�ýڵ�����
            childElement = element.addElement(elementName);
        }
        return childElement;
    }

    /**
     * ��Ӹ��ڵ�
     *
     * @param document Element �ڵ�
     * @param elementName String ����
     * @return Element
     */
    public static Element addRootElement(Document document, String elementName) {
        Element rootElement = null;
        //�жϽڵ������Ƿ�Ϊnull
        if (elementName == null) {
            elementName = "";
        }
        //�жϽڵ��Ƿ�Ϊnull
        if (document == null) {
        } else {
            //ȡ�ýڵ�����
            rootElement = document.addElement(elementName);
        }
        return rootElement;
    }

//	/**
//	 * ����ĳ��ֵ�Ƿ����
//	 * @return
//	 */
//	public static boolean selectNode(String str) {
//		SAXBuilder builder = new SAXBuilder();
//		Document doc = builder.build("kk.xml");
//		Element root = doc.getRootElement();
//
//		XPath xpath = XPath.newInstance("//�绰[../����='����'][@����='��ͥ']");
//		List list = xpath.selectNodes(root);
//		Iterator iter = list.iterator();
//		while (iter.hasNext()) {
//			Element item = (Element) iter.next();
//			System.err.println(item.getText());
//		}
//
//	}

}
