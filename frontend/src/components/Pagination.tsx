import React from 'react'

const Pagination = ({totalPages, actualPage, setPage }: any) => {

    const renderPages = () => {
        const pagesArray = Array.from({ length: totalPages }, (_, index) => index + 1);
        return pagesArray.map((page) => (
            <button
                key={page}
                onClick={() => setPage(page)}
            >
                {page}
            </button>
        ));
    };


    return (
        <div className="pagination-container">
            <div className="pagination">
                <button
                    className="navigation-button"
                    disabled={actualPage <= 1 }
                    onClick={() => setPage(actualPage - 1)}
                >
                    &laquo;
                </button>
                {renderPages()}
                <button
                    className="navigation-button"
                    disabled={actualPage >= totalPages }
                    onClick={() => setPage(actualPage + 1)}
                >
                    &raquo;
                </button>
            </div>
        </div>
    );
}

export default Pagination;