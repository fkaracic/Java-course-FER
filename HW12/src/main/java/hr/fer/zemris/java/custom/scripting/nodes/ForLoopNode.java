package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;

/**
 * {@code ForLoopNode} represents a node representing a single for-loop
 * construct.
 * 
 * @author Filip Karacic
 *
 */
public class ForLoopNode extends Node {
	/**
	 * Variable used in a for-loop construct.
	 */
	private ElementVariable variable;
	/**
	 * Expression from which for-loop construct starts.
	 */
	private Element startExpression;
	/**
	 * Expression at which for-loop construct ends.
	 */
	private Element endExpression;
	/**
	 * Expression of step for-loop construct. Can be null.
	 */
	private Element stepExpression;

	/**
	 * Initialize newly created object representing a for-loop construct.
	 * {@code stepExpression} can be {@code null}.
	 * 
	 * @param variable
	 *            variable of a for-loop construct
	 * @param startExpression
	 *            starting expression of a for-loop construct
	 * @param endExpression
	 *            ending expression of a for-loop construct
	 * @param stepExpression
	 *            step expression of a for-loop construct
	 * 
	 * @throws NullPointerException
	 *             if {@code variable} or {@code startExpression} or
	 *             {@code endExpression} is {@code null}
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression,
			Element stepExpression) {
		if (variable == null || startExpression == null || endExpression == null)
			throw new NullPointerException();

		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}

	/**
	 * Returns variable of this for-loop construct.
	 * 
	 * @return variable of this for-loop construct
	 */
	public ElementVariable getVariable() {
		return variable;
	}

	/**
	 * Returns starting expression of this for-loop construct.
	 * 
	 * @return starting expression of this for-loop construct
	 */
	public Element getStartExpression() {
		return startExpression;
	}

	/**
	 * Returns ending expression of this for-loop construct.
	 * 
	 * @return ending expression of this for-loop construct
	 */
	public Element getEndExpression() {
		return endExpression;
	}

	/**
	 * Returns step expression of this for-loop construct.
	 * 
	 * @return step expression of this for-loop construct
	 */
	public Element getStepExpression() {
		return stepExpression;
	}
	
	public void accept(INodeVisitor visitor) {
		visitor.visitForLoopNode(this);
	}

}
