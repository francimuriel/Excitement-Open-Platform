package ac.biu.nlp.nlp.engineml.small_unit_tests.old_small_tests;
import java.util.Set;

import ac.biu.nlp.nlp.engineml.representation.ExtendedInfo;
import ac.biu.nlp.nlp.engineml.representation.ExtendedNode;
import ac.biu.nlp.nlp.engineml.utilities.parsetreeutils.TreeUtilities;
import ac.biu.nlp.nlp.engineml.utilities.preprocess.ParserFactory;
import eu.excitementproject.eop.common.representation.parse.BasicParser;
import eu.excitementproject.eop.common.representation.parse.tree.TreeAndParentMap;
import eu.excitementproject.eop.common.representation.parse.tree.dependency.basic.BasicNode;
import eu.excitementproject.eop.common.utilities.ExceptionUtil;
import eu.excitementproject.eop.common.utilities.StringUtil;

public class DemoMissingLemmasInTree
{
	public static void main(String[] args)
	{
		try
		{
			@SuppressWarnings("deprecation")
			BasicParser parser = ParserFactory.getParser("localhost");
			parser.init();
			try
			{
				parser.setSentence("The ice is melting in the Arctic.");
				parser.parse();
				BasicNode originalTree = parser.getParseTree();
				ExtendedNode tree = TreeUtilities.copyFromBasicNode(originalTree);
				System.out.println(TreeUtilities.treeToString(tree));
				System.out.println(StringUtil.generateStringOfCharacter('-', 100));
				parser.setSentence("The ice is melting in Asia.");
				parser.parse();
				BasicNode tree2original = parser.getParseTree();
				ExtendedNode tree2 = TreeUtilities.copyFromBasicNode(tree2original);
				System.out.println(TreeUtilities.treeToString(tree2));
				System.out.println(StringUtil.generateStringOfCharacter('-', 100));
				TreeAndParentMap<ExtendedInfo, ExtendedNode> tapm1 = new TreeAndParentMap<ExtendedInfo, ExtendedNode>(tree);
				TreeAndParentMap<ExtendedInfo, ExtendedNode> tapm2 = new TreeAndParentMap<ExtendedInfo, ExtendedNode>(tree2);
				Set<String> tree2LemmasLowerCase = TreeUtilities.constructSetLemmasLowerCase(tapm2);
				double missingLemmasPortion = TreeUtilities.missingLemmasPortion(tapm1, tree2LemmasLowerCase);
				System.out.println(missingLemmasPortion);
				
				System.out.println(TreeUtilities.missingNodesPortion(tapm1, tapm2));
			}
			finally
			{
				parser.cleanUp();
			}
			
		}
		catch(Exception e)
		{
			ExceptionUtil.outputException(e, System.out);
		}
	}
}
