let currentSlide = 0;

function showSlide(index) {
    const slides = document.querySelectorAll('.image-container .slide');
    const totalSlides = slides.length;

    // Nếu index vượt quá số slide, reset lại về 0
    if (index >= totalSlides) {
        currentSlide = 0;
    } else if (index < 0) {
        currentSlide = totalSlides - 1;
    } else {
        currentSlide = index;
    }

    // Di chuyển slider
    const newTransformValue = -currentSlide * 100 + '%';
    document.querySelector('.image-container').style.transform = `translateX(${newTransformValue})`;
}

function moveSlide(direction) {
    showSlide(currentSlide + direction);
}

// Tự động hiển thị slide đầu tiên khi trang tải
document.addEventListener('DOMContentLoaded', () => {
    showSlide(currentSlide);
});
