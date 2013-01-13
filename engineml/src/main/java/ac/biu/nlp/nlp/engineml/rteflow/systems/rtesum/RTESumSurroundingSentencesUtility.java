package ac.biu.nlp.nlp.engineml.rteflow.systems.rtesum;
import org.apache.log4j.Logger;

import eu.excitementproject.eop.common.representation.parse.tree.dependency.view.TreeStringGenerator.TreeStringGeneratorException;

import ac.biu.nlp.nlp.engineml.representation.ExtendedInfo;
import ac.biu.nlp.nlp.engineml.representation.ExtendedNode;
import ac.biu.nlp.nlp.engineml.rteflow.systems.rtesum.preprocess.GenericPreprocessedTopicDataSet;
import ac.biu.nlp.nlp.engineml.utilities.parsetreeutils.TreeUtilities;


/**
 * 
 * @author Asher Stern
 * @since Nov 9, 2011
 *
 */
public class RTESumSurroundingSentencesUtility extends RTESumSurroundingSentencesUtilityGeneric<ExtendedInfo,ExtendedNode>
{
	public RTESumSurroundingSentencesUtility(GenericPreprocessedTopicDataSet<ExtendedInfo, ExtendedNode> extendedTopic) throws TreeStringGeneratorException
	{
		super(extendedTopic);
	}

	@Override
	protected void createBaseList() throws TreeStringGeneratorException
	{
		super.createBaseList();
		
		if (logger.isDebugEnabled())
		{
			logger.debug("Printing surroundingBaseList for this topic");
			StringBuffer sb = new StringBuffer();
			int index=0;
			for (TreeAndIdentifier<ExtendedInfo, ExtendedNode> surroundingTreeAndIdentifier : surroundingBaseList)
			{
				ExtendedNode surroundingTree = surroundingTreeAndIdentifier.getTree();
				sb.append("Tree #");
				sb.append(index);
				sb.append("\n");
				sb.append(TreeUtilities.treeToString(surroundingTree));
				sb.append("\n");
				++index;
			}
			int totalNumberOfSurroundingTrees = index;
			sb.append("Total: ");
			sb.append(totalNumberOfSurroundingTrees);
			sb.append(" trees");
			sb.append("\n");
			
			logger.debug(sb.toString());
		}
	}
	
	private static final Logger logger = Logger.getLogger(RTESumSurroundingSentencesUtility.class);
}
