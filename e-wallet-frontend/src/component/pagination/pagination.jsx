import React from 'react'
import ReactPaginate from 'react-paginate';

const Pagination = ({ totalPage, currenPage, setCurrentPage }) => {
  const pageNumbers = [];

  for (let i = 1; i <= totalPage; i++) {
    pageNumbers.push(i);
  }
  const handlePageClick = (event) => {
    //console.log(event.selected)
    setCurrentPage(event.selected)

  };
  return (
    <div>
      <ReactPaginate
        breakLabel="..."
        nextLabel=">"
        onPageChange={handlePageClick}
        pageRangeDisplayed={5}
        pageCount={totalPage}
        previousLabel="<"
        renderOnZeroPageCount={null}
        breakClassName={'rpage-item'}
        breakLinkClassName={'rpage-link'}
        containerClassName={'pagination'}
        pageClassName={'rpage-item'}
        pageLinkClassName={'rpage-link'}
        previousClassName={'rpage-item'}
        previousLinkClassName={'rpage-link'}
        nextClassName={'rpage-item'}
        nextLinkClassName={'rpage-link'}
        
        activeClassName={'active'}
      />
      {/* <ul className='pagination'>
          
            {pageNumbers.map(item => (
                <li style={{margin:`5px`}} key={item} onClick={e => setCurrentPage(item-1)}>
                    {item}
                </li>
            ))}
        </ul> */}
    </div>
  )
}

export default Pagination