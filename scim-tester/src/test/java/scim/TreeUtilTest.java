package scim;

import java.io.FileWriter;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.google.gson.GsonBuilder;
import com.raonscn.scim.node.ScimConnectionNode;
import com.raonscn.scim.node.ScimFolderNode;
import com.raonscn.scim.node.ScimNode;
import com.raonscn.scim.node.ScimNodeList;
import com.raonscn.scim.node.ScimServerNode;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class TreeUtilTest {

	@Test
	public void make_tree_test() {
		//Map<String, ScimNode> agent_tree = new HashMap<String, ScimNode>();
		ScimNodeList root = new ScimNodeList();
		ScimNode folder_1 = new ScimFolderNode("folder1");
		{
			ScimNode server_11 = new ScimServerNode("server11");
			{
				ScimNode connection_111 = new ScimConnectionNode("connection111");
				server_11.addChild(connection_111);
			}
			folder_1.addChild(server_11);
		}
		root.add(folder_1);
		
		ScimNode folder_2 = new ScimFolderNode("folder2");
		{
			ScimNode folder_21 = new ScimFolderNode("folder21");
			{
				ScimNode server_211 = new ScimServerNode("server211");
				{
					ScimNode connection_2111 = new ScimConnectionNode("connection2111");
					server_211.addChild(connection_2111);
					
					ScimNode connection_2112 = new ScimConnectionNode("connection2112");
					server_211.addChild(connection_2112);
				}
				folder_21.addChild(server_211);
			}
			ScimNode folder_22 = new ScimFolderNode("folder22");
			
			folder_2.addChild(folder_21);
			folder_2.addChild(folder_22);
		}
		root.add(folder_2);
		
		
//		
//		ScimNode group_11 = new ScimNode("group11", ScimNodeType.folder);
//		ScimNode group_21 = new ScimNode("group21", ScimNodeType.folder);
//		ScimNode group_22 = new ScimNode("group22", ScimNodeType.folder);
//		ScimNode group_23 = new ScimNode("group22", ScimNodeType.folder);
//		group_1.addChild(group_11);
//		group_2.addChild(group_21);
//		group_2.addChild(group_22);
//		group_2.addChild(group_23);
//		
//		
//		ScimNode server_12 = new ScimNode("server12", ScimNodeType.server);
//		ScimNode server_111 = new ScimNode("server111", ScimNodeType.server);
//		group_1.addChild(server_12);
//		group_11.addChild(server_111);
//		
//		ScimNode server_21 = new ScimNode("server21", ScimNodeType.server);
//		ScimNode server_211 = new ScimNode("server211", ScimNodeType.server);
//		group_2.addChild(server_21);
//		group_21.addChild(server_211);
		
		
		
		log.debug("{}",root);
		log.debug("{}",new GsonBuilder().setPrettyPrinting().create().toJson(root));
		
		try(FileWriter file_writer = new FileWriter("./data/agent-node.json"))
		{
			new GsonBuilder().setPrettyPrinting().create().toJson(root, file_writer);
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			
		}
		
	}
	
}
