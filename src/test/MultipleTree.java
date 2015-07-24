package test;  
  
import java.util.ArrayList;  
import java.util.Comparator;  
import java.util.HashMap;  
import java.util.Iterator;  
import java.util.List;  
import java.util.Map;  
import java.util.Set;  
import java.util.Collections;  
  
/** 
 * 多叉树类 
*/  
public class MultipleTree {  
 @SuppressWarnings({ "unchecked", "rawtypes" })
public static void main(String[] args) {  
  // 读取层次数据结果集列表   
  List dataList = VirtualDataGenerator.getVirtualResult();    
    
  // 节点列表（散列表，用于临时存储节点对象）  
  HashMap nodeList = new HashMap();  
  // 根节点  
  Node root = null;  
  // 根据结果集构造节点列表（存入散列表）  
  for (Iterator it = dataList.iterator(); it.hasNext();) {  
   Map dataRecord = (Map) it.next();  
   Node node = new Node();  
   node.id = (String) dataRecord.get("id");  
   node.text = (String) dataRecord.get("text");  
   node.parentId = (String) dataRecord.get("parentId");  
   nodeList.put(node.id, node);  
  }  
  // 构造无序的多叉树  
  Set entrySet = nodeList.entrySet();  
  for (Iterator it = entrySet.iterator(); it.hasNext();) {  
   Node node = (Node) ((Map.Entry) it.next()).getValue();  
   if (node.parentId == null || node.parentId.equals("")) {  
    root = node;  
   } else {  
    ((Node) nodeList.get(node.parentId)).addChild(node);  
   }  
  }  
  // 输出无序的树形菜单的JSON字符串  
  System.out.println(root.toString());     
  // 对多叉树进行横向排序  
  root.sortChildren();  
  // 输出有序的树形菜单的JSON字符串  
  System.out.println(root.toString());   
 }  
     
}  
  
  
/** 
* 节点类 
*/  
class Node {  
 /** 
  * 节点编号 
  */  
 public String id;  
 /** 
  * 节点内容 
  */  
 public String text;  
 /** 
  * 父节点编号 
  */  
 public String parentId;  
 /** 
  * 孩子节点列表 
  */  
 private Children children = new Children();  
   
 // 先序遍历，拼接JSON字符串  
 public String toString() {    
  String result = "{"  
   + "id : '" + id + "'"  
   + ", text : '" + text + "'";  
    
  if (children != null && children.getSize() != 0) {  
   result += ", children : " + children.toString();  
  } else {  
   result += ", leaf : true";  
  }  
      
  return result + "}";  
 }  
   
 // 兄弟节点横向排序  
 public void sortChildren() {  
  if (children != null && children.getSize() != 0) {  
   children.sortChildren();  
  }  
 }  
   
 // 添加孩子节点  
 public void addChild(Node node) {  
  this.children.addChild(node);  
 }  
}  
  
/** 
* 孩子列表类 
*/  
class Children {  
 @SuppressWarnings("rawtypes")
private List list = new ArrayList();  
   
 public int getSize() {  
  return list.size();  
 }  
   
 @SuppressWarnings("unchecked")
public void addChild(Node node) {  
  list.add(node);  
 }  
   
 // 拼接孩子节点的JSON字符串  
 public String toString() {  
  String result = "[";    
  for (@SuppressWarnings("rawtypes")
Iterator it = list.iterator(); it.hasNext();) {  
   result += ((Node) it.next()).toString();  
   result += ",";  
  }  
  result = result.substring(0, result.length() - 1);  
  result += "]";  
  return result;  
 }  
   
 // 孩子节点排序  
 @SuppressWarnings("unchecked")
public void sortChildren() {  
  // 对本层节点进行排序  
  // 可根据不同的排序属性，传入不同的比较器，这里传入ID比较器  
  Collections.sort(list, new NodeIDComparator());  
  // 对每个节点的下一层节点进行排序  
  for (@SuppressWarnings("rawtypes")
Iterator it = list.iterator(); it.hasNext();) {  
   ((Node) it.next()).sortChildren();  
  }  
 }  
}  
  
/** 
 * 节点比较器 
 */  
@SuppressWarnings("rawtypes")
class NodeIDComparator implements Comparator {  
 // 按照节点编号比较  
 public int compare(Object o1,Object o2){
	 int j1=Integer.parseInt(((Node)o1).id);
	 int j2=Integer.parseInt(((Node)o2).id);
	return (j1<j2?-1:(j1==j2?0:1));
 }
}  
  
/** 
 * 构造虚拟的层次数据 
 */  
class VirtualDataGenerator {  
 // 构造无序的结果集列表，实际应用中，该数据应该从数据库中查询获得；  
 @SuppressWarnings({ "unchecked", "rawtypes" })
public static List getVirtualResult() {      
  List dataList = new ArrayList();  
    
  HashMap dataRecord1 = new HashMap();  
  dataRecord1.put("id", "0002");  
  dataRecord1.put("text", "6lowpan温度传感器");  
  dataRecord1.put("parentId", "0001");  
    
  HashMap<String,String> dataRecord2 = new HashMap<String,String>();  
  dataRecord2.put("id", "0003");  
  dataRecord2.put("text", "6lowpan温度传感器");  
  dataRecord2.put("parentId", "0001");  
    
  HashMap<String,String> dataRecord3 = new HashMap<String,String>();  
  dataRecord3.put("id", "0004");  
  dataRecord3.put("text", "6lowpan温度传感器");  
  dataRecord3.put("parentId", "0000");  
        
  HashMap<String,String> dataRecord4 = new HashMap<String,String>();  
  dataRecord4.put("id", "0005");  
  dataRecord4.put("text", "6lowpan温度传感器");  
  dataRecord4.put("parentId", "0000");  
        
  HashMap<String,String> dataRecord5 = new HashMap<String,String>();  
  dataRecord5.put("id", "0000");  
  dataRecord5.put("text", "初始协调点");  
  dataRecord5.put("parentId", "");  
    
  HashMap<String,String> dataRecord6 = new HashMap<String,String>();  
  dataRecord6.put("id", "0006");  
  dataRecord6.put("text", "6lowpan温度传感器");  
  dataRecord6.put("parentId", "0000");  
    
  HashMap<String,String> dataRecord7 = new HashMap<String,String>();  
  dataRecord7.put("id", "0007");  
  dataRecord7.put("text", "6lowpan温度传感器");  
  dataRecord7.put("parentId", "0000");    
   
  HashMap<String,String> dataRecord8=new HashMap<String,String>();
  dataRecord8.put("id", "0001");
  dataRecord8.put("text","6lowpan温度传感器");
  dataRecord8.put("parentId","0000");
  
  HashMap<String,String> dataRecord9=new HashMap<String,String>();
  dataRecord9.put("id", "0008");
  dataRecord9.put("text","6lowpan温度传感器");
  dataRecord9.put("parentId","0000");
  dataList.add(dataRecord1);  
  dataList.add(dataRecord2);  
  dataList.add(dataRecord3);  
  dataList.add(dataRecord4);  
  dataList.add(dataRecord5);  
  dataList.add(dataRecord6);  
  dataList.add(dataRecord7); 
  dataList.add(dataRecord8);
  dataList.add(dataRecord9);
  
    /**
     * 代码是Cursor cursor = db.rawQuery("select threadid, downlength from filedownlog where downpath=?", new String[]{path});
     * //从filedownlog中查出指定路径的线程id，下载的长度		
     * Map<Integer, Integer> data = new HashMap<Integer, Integer>();
     * //hashmap对象		
     * while(cursor.moveToNext()){
     * //迭代			
     * data.put(cursor.getInt(0), cursor.getInt(1));
     * //放入map中		
     * } 
     */
  return dataList;  
 }   
}  