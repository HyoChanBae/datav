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
    const resizer = element.querySelector('.resize-handle');
    resizer.addEventListener('mousedown', initResize);

    function initResize(e) {
        e.preventDefault();
        window.addEventListener('mousemove', resize);
        window.addEventListener('mouseup', stopResize);
    }

    function resize(e) {
        element.style.width = (e.clientX - element.offsetLeft) + 'px';
        element.style.height = (e.clientY - element.offsetTop) + 'px';
    }

    function stopResize() {
        window.removeEventListener('mousemove', resize);
        window.removeEventListener('mouseup', stopResize);
    }
}
