package texas.sbq.travel.domains;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import lombok.Data;
import texas.sbq.travel.utils.Proxy;


@Data
@Component("pager")
@Lazy
public class Pager extends Proxy{
	private int rowCount,startRow,endRow,
				pageSize, pageCount,startPage,endPage,nowPage,
				blockSize,blockCount,nowBlock,prevBlock,nextBlock;
	private boolean existPrev,existNext;
	private String search, option, ctype;
	
	
	
	public void paging() {
		pageCount =(rowCount % pageSize !=0)? rowCount /pageSize+1 : rowCount /pageSize; 
        blockCount = (pageCount % blockSize !=0)? pageCount /blockSize+1 : pageCount /blockSize; 
        startRow = nowPage * pageSize;
        endRow = (nowPage != (pageCount-1))?startRow + (pageSize -1) :rowCount-1 ;
        nowBlock = nowPage / blockSize;
        startPage = nowBlock * blockSize;
        endPage = (nowBlock != (blockCount-1))?startPage + (blockSize -1) :pageCount-1 ;
        prevBlock = startPage - blockSize;
        nextBlock = startPage + blockSize;
        existPrev = nowBlock != 0;
        existNext = nowBlock != blockCount-1;
		
	}
	
}