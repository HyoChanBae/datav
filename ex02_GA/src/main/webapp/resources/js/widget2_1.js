document.addEventListener('DOMContentLoaded', () => {
    const widgets = document.querySelectorAll('.widget');

    widgets.forEach(widget => {
        makeDraggable(widget);
        makeResizable(widget);
    });
});

function makeDraggable(element) {
    let offsetX = 0, offsetY = 0, initialX = 0, initialY = 0;

    element.onmousedown = dragMouseDown;

    function dragMouseDown(e) {
        e.preventDefault();
        if (e.target.classList.contains('resize-handle')) {
            return; // Prevent dragging when clicking on resize handle
        }
        initialX = e.clientX;
        initialY = e.clientY;
        offsetX = initialX - element.getBoundingClientRect().left;
        offsetY = initialY - element.getBoundingClientRect().top;
        document.onmouseup = closeDragElement;
        document.onmousemove = elementDrag;
        element.style.zIndex = 1000; // Bring the dragged element to the front
    }

    function elementDrag(e) {
        e.preventDefault();
        element.style.top = (e.clientY - offsetY) + "px";
        element.style.left = (e.clientX - offsetX) + "px";
    }

    function closeDragElement() {
        document.onmouseup = null;
        document.onmousemove = null;
        element.style.zIndex = ''; // Reset zIndex
    }
}

function makeResizable(element) {
    const handles = element.querySelectorAll('.resize-handle');
    handles.forEach(handle => {
        handle.addEventListener('mousedown', initResize);
    });

    function initResize(e) {
        e.preventDefault();
        const handle = e.target;
        window.addEventListener('mousemove', resize);
        window.addEventListener('mouseup', stopResize);

        function resize(e) {
            if (handle.classList.contains('se')) {
                element.style.width = (e.clientX - element.getBoundingClientRect().left) + 'px';
                element.style.height = (e.clientY - element.getBoundingClientRect().top) + 'px';
            } /*else if (handle.classList.contains('sw')) {
                element.style.width = (element.getBoundingClientRect().right - e.clientX) + 'px';
                element.style.height = (e.clientY - element.getBoundingClientRect().top) + 'px';
                element.style.left = e.clientX + 'px';
            } else if (handle.classList.contains('ne')) {
                element.style.width = (e.clientX - element.getBoundingClientRect().left) + 'px';
                element.style.height = (element.getBoundingClientRect().bottom - e.clientY) + 'px';
                element.style.top = e.clientY + 'px';
            } else if (handle.classList.contains('nw')) {
                element.style.width = (element.getBoundingClientRect().right - e.clientX) + 'px';
                element.style.height = (element.getBoundingClientRect().bottom - e.clientY) + 'px';
                element.style.left = e.clientX + 'px';
                element.style.top = e.clientY + 'px';
            }*/
        }

        function stopResize() {
            window.removeEventListener('mousemove', resize);
            window.removeEventListener('mouseup', stopResize);
        }
    }
}
