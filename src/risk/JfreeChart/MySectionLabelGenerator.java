/**PieChart 라벨인터페이스 
 ** @author 임승철 2009.09.22
 *  
 */
package risk.JfreeChart;
import org.jfree.chart.*;
import org.jfree.data.general.PieDataset;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import java.text.AttributedString;
import java.util.Iterator;

//PieSectionLabelGenerator  상속 받아 인터페이스 구현
public class MySectionLabelGenerator implements PieSectionLabelGenerator{	
        
		public MySectionLabelGenerator()
		{
				
		}
		/*
		public String generateSectionLabel(PieDataset aDataset, Comparable aKey) {
	          String labelResult = null;
	          double total = 0 ;         	         
	          if (aDataset != null)
	          {
	        	  for( int i=0 ; i<aDataset.getItemCount() ; i++ )
	        	  {	        		  
	        		  total += (aDataset.getValue(i)).doubleValue();
	        	  }

	             //화면에 보여질 text를 표시
	             //labelResult = Math.round(aDataset.getValue(aKey).doubleValue()/total*100) + "%";
	        	 labelResult = Math.round(aDataset.getValue(aKey).doubleValue()) + "건";
	          }
	          return labelResult;

	    }
	    */

		
		public String generateSectionLabel(PieDataset aDataset, Comparable aKey) {
	           String labelResult = null;
	           double total = 0 ;                   
	           if (aDataset != null)
	           {
	            for( int i=0 ; i<aDataset.getItemCount() ; i++ )
	            {             
	             total += (aDataset.getValue(i)).doubleValue();
	            }
	            if(Math.round(aDataset.getValue(aKey).doubleValue()/total*100) != 0){
	             //labelResult = aDataset.getKey(aDataset.getIndex(aKey))+" ("+Math.round(aDataset.getValue(aKey).doubleValue()/total*100) + "%)";
	            	//labelResult = Math.round(aDataset.getValue(aKey).doubleValue()/total*100) + "%";
	            }
	            	labelResult = Math.round(aDataset.getValue(aKey).doubleValue()) + "건";
	           }
	           return labelResult;

	     }
		
        public AttributedString generateAttributedSectionLabel(PieDataset arg0, Comparable arg1) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

}
