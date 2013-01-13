package ac.biu.nlp.nlp.engineml.operations.finders;
import org.apache.log4j.Logger;

import ac.biu.nlp.nlp.engineml.datastructures.CanonicalLemmaAndPos;
import ac.biu.nlp.nlp.engineml.operations.OperationException;
import ac.biu.nlp.nlp.engineml.operations.rules.ByLemmaPosLexicalRuleBase;
import ac.biu.nlp.nlp.engineml.operations.rules.LexicalRule;
import ac.biu.nlp.nlp.engineml.operations.rules.RuleBaseException;
import ac.biu.nlp.nlp.engineml.representation.ExtendedInfo;
import ac.biu.nlp.nlp.engineml.representation.ExtendedNode;
import ac.biu.nlp.nlp.engineml.utilities.TeEngineMlException;
import eu.excitementproject.eop.common.datastructures.immutable.ImmutableSet;
import eu.excitementproject.eop.common.representation.parse.tree.TreeAndParentMap;
import eu.excitementproject.eop.common.utilities.StringUtil;

/**
 * Finds relevant lexical rules, but only rules that their right-hand-side exists in the hypothesis.
 * 
 * @author Asher Stern
 * @since Feb 24, 2011
 *
 */
public class Substitution2DLexicalRuleByLemmaPosFinder<T extends LexicalRule> extends SubstitutionLexicalRuleByLemmaPosFinder<T>
{
	
	
//	public Substitution2DLexicalRuleByLemmaPosFinder(TreeAndParentMap<ExtendedInfo, ExtendedNode> treeAndParentMap, ByLemmaPosLexicalRuleBase<T> ruleBase, String ruleBaseName,ImmutableSet<CanonicalLemmaAndPos> hypothesisLemmas, ImmutableSet<String> hypothesisLemmasOnly) throws OperationException
//	{
//		super(treeAndParentMap, ruleBase, ruleBaseName);
//		this.hypothesisLemmasAndPoses = hypothesisLemmas;
//		this.hypothesisLemmasOnly = hypothesisLemmasOnly;
//	}
	
	public Substitution2DLexicalRuleByLemmaPosFinder(
			TreeAndParentMap<ExtendedInfo, ExtendedNode> treeAndParentMap,
			ByLemmaPosLexicalRuleBase<T> ruleBase, String ruleBaseName,
			boolean filterLeftStopWords, boolean filterRightStopWords,
			ImmutableSet<String> stopWords,
			ImmutableSet<CanonicalLemmaAndPos> hypothesisLemmas, ImmutableSet<String> hypothesisLemmasOnly) throws OperationException
	{
		super(treeAndParentMap, ruleBase, ruleBaseName, filterLeftStopWords,
				filterRightStopWords, stopWords);
		this.hypothesisLemmasAndPoses = hypothesisLemmas;
		this.hypothesisLemmasOnly = hypothesisLemmasOnly;
	}

	protected boolean isRelevantRule(T rule) throws RuleBaseException
	{
		boolean ret = false;
		try
		{
			if (super.isRelevantRule(rule))
			{
				if (StringUtil.setContainsIgnoreCase(hypothesisLemmasOnly, rule.getRhsLemma())) // if it is in the hypothesis
				{
					CanonicalLemmaAndPos original = new CanonicalLemmaAndPos(rule.getLhsLemma(), rule.getLhsPos());
					// old if (!DsUtils.containsCanonical(hypothesisLemmasAndPoses, original)) // (old) if (!hypothesisLemmasAndPoses.contains(original)) // if the left hand side is not in the hypothesis
					if (!hypothesisLemmasAndPoses.contains(original))
					{
						ret = true;
					}
				}
			}
		}
		catch(TeEngineMlException e)
		{
			throw new RuleBaseException("Problem with the given lemma and part-of-speech",e);
		}

		return ret;
	}
	
	
	
	private ImmutableSet<CanonicalLemmaAndPos> hypothesisLemmasAndPoses;
	private ImmutableSet<String> hypothesisLemmasOnly;
	
	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(Substitution2DLexicalRuleByLemmaPosFinder.class);
}
