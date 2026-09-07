"""One-off generator for WordFlow launcher icon rasters (legacy mipmaps + Play Store hi-res icon).

Renders the same background/foreground vectors used by res/drawable/ic_launcher*.xml so the
raster fallbacks match the adaptive icon shown on API 26+.
"""
import math
import os

from PIL import Image, ImageDraw

BACKGROUND_COLOR = (0x2E, 0x7D, 0x6B, 255)
STROKE_COLOR = (0xFF, 0xFF, 0xFF, 255)
VIEWPORT = 108
STROKE_WIDTH = 9
PATH_POINTS = [(30, 34), (40, 74), (54, 46), (68, 74), (78, 34)]

SUPERSAMPLE = 4

DENSITIES = {
    "mdpi": 48,
    "hdpi": 72,
    "xhdpi": 96,
    "xxhdpi": 144,
    "xxxhdpi": 192,
}

REPO_ROOT = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
RES_DIR = os.path.join(REPO_ROOT, "app", "src", "main", "res")
STORE_DIR = os.path.join(REPO_ROOT, "playstore")


def render_icon(size, round_mask):
    ss = size * SUPERSAMPLE
    img = Image.new("RGBA", (ss, ss), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)

    draw.rectangle([0, 0, ss, ss], fill=BACKGROUND_COLOR)

    scale = ss / VIEWPORT
    stroke_px = STROKE_WIDTH * scale
    scaled_points = [(x * scale, y * scale) for x, y in PATH_POINTS]

    draw.line(scaled_points, fill=STROKE_COLOR, width=round(stroke_px), joint="curve")
    radius = stroke_px / 2
    for x, y in scaled_points:
        draw.ellipse([x - radius, y - radius, x + radius, y + radius], fill=STROKE_COLOR)

    if round_mask:
        mask = Image.new("L", (ss, ss), 0)
        mask_draw = ImageDraw.Draw(mask)
        mask_draw.ellipse([0, 0, ss, ss], fill=255)
        img.putalpha(mask)

    return img.resize((size, size), Image.LANCZOS)


def main():
    for density, size in DENSITIES.items():
        out_dir = os.path.join(RES_DIR, f"mipmap-{density}")
        os.makedirs(out_dir, exist_ok=True)
        render_icon(size, round_mask=False).save(os.path.join(out_dir, "ic_launcher.png"))
        render_icon(size, round_mask=True).save(os.path.join(out_dir, "ic_launcher_round.png"))
        print(f"wrote mipmap-{density} ({size}x{size})")

    os.makedirs(STORE_DIR, exist_ok=True)
    render_icon(512, round_mask=False).save(os.path.join(STORE_DIR, "ic_launcher_hires_512.png"))
    print("wrote playstore/ic_launcher_hires_512.png (upload as the Play Console hi-res icon)")


if __name__ == "__main__":
    main()
