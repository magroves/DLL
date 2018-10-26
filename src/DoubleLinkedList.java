
public class DoubleLinkedList<T> implements ListInterface<T> {
	private DoubleLinkedNode first;
	private DoubleLinkedNode last;
	private int numElements;

	public DoubleLinkedList() {
		
	}

	@Override
	public void add (T newEntry) {
		DoubleLinkedNode newNode = new DoubleLinkedNode(newEntry);
		
		if(isEmpty()) {
			first = newNode;
			last = newNode;
		}
		else {
			last = getNodeAt(numElements);
			last.setNextNode(newNode);
			newNode.setPreviousNode(last);
			last = newNode;
		}

		numElements++;
	}

	@Override
	public void add (int newPosition, T newEntry) {
		if ((newPosition >= 1) && (newPosition <= numElements + 1)){
			
			DoubleLinkedNode newNode = new DoubleLinkedNode(newEntry);
			
			if (newPosition == 1) { // add to front
				if(isEmpty()) {
					first = newNode;
					last = newNode;
				}
				else {
					newNode.setNextNode(first);
					first = newNode;
				}
			}
			
			else if (newPosition > numElements) {
				DoubleLinkedNode nodeBefore = getNodeAt(newPosition - 1);
				newNode.setPreviousNode(nodeBefore);
				nodeBefore.setNextNode(newNode);
				last = newNode;
				
			}
			
			else { // list is not empty
				
				DoubleLinkedNode nodeBefore = getNodeAt(newPosition - 1);
				DoubleLinkedNode nodeAfter = nodeBefore.getNextNode();
				
				newNode.setNextNode(nodeAfter);
				nodeAfter.setPreviousNode(newNode);
				
				nodeBefore.setNextNode(newNode);
				newNode.setPreviousNode(nodeBefore);
				if(newPosition >= numElements ) {
					last = newNode;
				}
			}
			numElements++;
		}
		else
			throw new IndexOutOfBoundsException(
					"Illegal position given to add operation.");
	}

	@Override
	public T remove (int givenPosition) {
		T result = null; // Return value
		if ((givenPosition >= 1) && (givenPosition <= numElements)) {

			if (givenPosition == 1) { // Case 1: Remove first entry
				
				if (first.equals(last)) {
					result = first.getData();
					clear();
				}

				else {
					result = first.getData(); // Save entry to be removed
					first = first.getNextNode(); // Remove entry
					first.setPreviousNode(null);
					numElements--; // Update count
				}

			}
			
			else { // Case 2: Not first entry
				DoubleLinkedNode nodeBefore = getNodeAt(givenPosition - 1);
				DoubleLinkedNode nodeToRemove = nodeBefore.getNextNode();
				result = nodeToRemove.getData(); // Save entry to be removed
				DoubleLinkedNode nodeAfter = nodeToRemove.getNextNode();
				nodeBefore.setNextNode(nodeAfter); // Remove entry
				numElements--; // Update count
			}
			
			
			return result; // Return removed entry
		}
		else
			throw new IndexOutOfBoundsException(
					"Illegal position given to remove operation.");
	}

	@Override
	public void clear () {
		first = null;
		numElements = 0;
	}

	@Override
	public T replace (int givenPosition, T newEntry) {
		if ((givenPosition >= 1) && (givenPosition <= numElements)) {
			DoubleLinkedNode desiredNode = getNodeAt(givenPosition);
			T originalEntry = desiredNode.getData();
			desiredNode.setData(newEntry);
			return originalEntry;
		}
		else
			throw new IndexOutOfBoundsException("Illegal position given to replace operation.");
	}

	@Override
	public T getEntry (int givenPosition) {
		if ((givenPosition >= 1) && (givenPosition <= numElements))	{
			assert !isEmpty();
			return getNodeAt(givenPosition).getData();
		}
		else
			throw new IndexOutOfBoundsException("Illegal position given to getEntry operation.");
	}

	@Override
	public T[] toArray() {
		
		@SuppressWarnings("unchecked")
		T[] result = (T[])new Object[numElements];
		int index = 0;
		DoubleLinkedNode currentNode = first;
		
		while ((index < numElements) && (currentNode != null)) {
			result[index] = currentNode.getData();
			currentNode = currentNode.getNextNode();
			index++;
		}
		return result;
	}

	@Override
	public boolean contains (T anEntry) {
		boolean found = false;
		DoubleLinkedNode currentNode = first;
		while (!found && (currentNode != null)) {
			if (anEntry.equals(currentNode.getData()))
				found = true;
			else
				currentNode = currentNode.getNextNode();
		}
		return found;
	}

	@Override
	public int getLength() {
		return numElements;
	}

	@Override
	public boolean isEmpty() {
		boolean result;
		if (numElements == 0) {
			result = true;
		}
		else {
			result = false;
		}
		return result;
	} 


// Initializes the class's data fields to indicate an empty list.
	private void initializeDataFields()  {
		first = null;
		last = null;
		numElements = 0;
	} 

	protected DoubleLinkedNode getFirst() {
		return first;
	}

	protected DoubleLinkedNode getLast() {
		return last;
	}

	// Returns a reference to the node at a given position.
	// Precondition: The chain is not empty;
	//               1 <= givenPosition <= numberOfEntries.	
	protected DoubleLinkedNode getNodeAt(int givenPosition)	{
		assert !isEmpty() && (1 <= givenPosition) && (givenPosition <= numElements);
		DoubleLinkedNode currentNode = first;

		// Traverse the chain to locate the desired node
		// (skipped if givenPosition is 1)
		for (int counter = 1; counter < givenPosition; counter++)
			currentNode = currentNode.getNextNode();

		assert currentNode != null;

		return currentNode;
	} 

	
	protected class DoubleLinkedNode{
		private T data;  	 
		private DoubleLinkedNode next;  	 // Link to next node
		private DoubleLinkedNode previous; // Link to previous node

		private DoubleLinkedNode(T dataPortion){
			data = dataPortion;
			next = null;	
			previous = null;	
		}
		private DoubleLinkedNode(DoubleLinkedNode previousNode, T dataPortion, DoubleLinkedNode nextNode){
			data = dataPortion;
			next = nextNode;	
			previous = previousNode;
		} 

		protected T getData(){
			return data;
		} 

		private void setData(T newData){
			data = newData;
		} 

		DoubleLinkedNode getNextNode(){
			return next;
		} 

		private void setNextNode(DoubleLinkedNode nextNode){
			next = nextNode;
		} 

		DoubleLinkedNode getPreviousNode(){
			return previous;
		}

		private void setPreviousNode(DoubleLinkedNode previousNode){
			previous = previousNode;
		}
	}
}
