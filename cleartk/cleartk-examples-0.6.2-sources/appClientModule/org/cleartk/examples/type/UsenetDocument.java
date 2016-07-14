

/* First created by JCasGen Tue Aug 14 17:08:39 MDT 2012 */
package org.cleartk.examples.type;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Tue Aug 14 17:08:39 MDT 2012
 * XML source: file:/data/llbecker/cleartk/cleartk-examples/target/checkout/src/main/resources/org/cleartk/examples/TypeSystem.xml
 * @generated */
public class UsenetDocument extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(UsenetDocument.class);
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int type = typeIndexID;
  /** @generated  */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected UsenetDocument() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public UsenetDocument(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public UsenetDocument(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public UsenetDocument(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** <!-- begin-user-doc -->
    * Write your own initialization here
    * <!-- end-user-doc -->
  @generated modifiable */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: category

  /** getter for category - gets The category label for the document (e.g. sports, entertainment, politics ...)
   * @generated */
  public String getCategory() {
    if (UsenetDocument_Type.featOkTst && ((UsenetDocument_Type)jcasType).casFeat_category == null)
      jcasType.jcas.throwFeatMissing("category", "org.cleartk.examples.type.UsenetDocument");
    return jcasType.ll_cas.ll_getStringValue(addr, ((UsenetDocument_Type)jcasType).casFeatCode_category);}
    
  /** setter for category - sets The category label for the document (e.g. sports, entertainment, politics ...) 
   * @generated */
  public void setCategory(String v) {
    if (UsenetDocument_Type.featOkTst && ((UsenetDocument_Type)jcasType).casFeat_category == null)
      jcasType.jcas.throwFeatMissing("category", "org.cleartk.examples.type.UsenetDocument");
    jcasType.ll_cas.ll_setStringValue(addr, ((UsenetDocument_Type)jcasType).casFeatCode_category, v);}    
  }

    