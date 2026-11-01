"""Deploy the image built by CI and fail the job if Render cannot run it."""

import json
import os
import time
import urllib.error
import urllib.request


def main():
    api_key = os.environ["RENDER_API_KEY"]
    service_id = os.environ["RENDER_SERVICE_ID"]
    image_url = os.environ["IMAGE_URL"]
    if not api_key or not service_id or "@sha256:" not in image_url:
        raise SystemExit("Set the Render API key, service ID, and image digest.")

    def request(path, payload=None):
        req = urllib.request.Request(
            f"https://api.render.com/v1/services/{service_id}{path}",
            data=json.dumps(payload).encode() if payload is not None else None,
            headers={
                "Authorization": f"Bearer {api_key}",
                "Content-Type": "application/json",
            },
        )
        try:
            with urllib.request.urlopen(req, timeout=30) as response:
                return json.load(response)
        except urllib.error.HTTPError as error:
            raise SystemExit(f"Render API returned HTTP {error.code} for {path}.") from None

    service = request("")
    if service["serviceDetails"]["runtime"] != "image":
        raise SystemExit("Switch the Render service to a prebuilt image before enabling CI deploys.")
    deploy = request("/deploys", {"imageUrl": image_url})
    print(f"Deploying {image_url}, deploy {deploy['id']}", flush=True)
    deadline = time.monotonic() + 15 * 60
    while time.monotonic() < deadline:
        deploy = request(f"/deploys/{deploy['id']}")
        status = deploy["status"]
        print(f"Render status: {status}", flush=True)
        if status == "live":
            return
        if status in {"build_failed", "update_failed", "pre_deploy_failed", "canceled", "deactivated"}:
            raise SystemExit(f"Render deployment failed: {status}")
        time.sleep(15)
    raise SystemExit("Timed out waiting for Render deployment.")


if __name__ == "__main__":
    main()
